package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Projection.ButcheryBatchDetailItemAggregatedView;
import com.toolkit.inventory.Projection.ButcheryBatchDetailItemInventoryView;
import com.toolkit.inventory.Projection.ButcheryBatchInventorySummary;
import com.toolkit.inventory.Repository.*;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryBatchServiceImp implements ButcheryBatchService{

    final ButcheryBatchRepository butcheryBatchRepository;
    final ButcheryBatchDetailRepository butcheryBatchDetailRepository;
    final ButcheryBatchDetailItemRepository butcheryBatchDetailItemRepository;
    final ButcheryBatchInventoryRepository butcheryBatchInventoryRepository;
    final VendorWarehouseRepository vendorWarehouseRepository;
    final VendorRepository vendorRepository;
    final ItemRepository itemRepository;
    final UomRepository uomRepository;
    final ItemUomRepository itemUomRepository;
    final UserRepository userRepository;

    @Autowired
    public ButcheryBatchServiceImp(
            ButcheryBatchRepository butcheryBatchRepository,
            ButcheryBatchDetailRepository butcheryBatchDetailRepository,
            ButcheryBatchDetailItemRepository butcheryBatchDetailItemRepository,
            ButcheryBatchInventoryRepository butcheryBatchInventoryRepository,
            VendorWarehouseRepository vendorWarehouseRepository,
            VendorRepository vendorRepository,
            ItemRepository itemRepository,
            UomRepository uomRepository,
            ItemUomRepository itemUomRepository,
            UserRepository userRepository
    ) {
        this.butcheryBatchRepository = butcheryBatchRepository;
        this.butcheryBatchDetailRepository = butcheryBatchDetailRepository;
        this.butcheryBatchDetailItemRepository = butcheryBatchDetailItemRepository;
        this.butcheryBatchInventoryRepository = butcheryBatchInventoryRepository;
        this.vendorWarehouseRepository = vendorWarehouseRepository;
        this.vendorRepository = vendorRepository;
        this.itemRepository = itemRepository;
        this.uomRepository = uomRepository;
        this.itemUomRepository = itemUomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ButcheryBatchDto getButcheryBatch(Long butcheryBatchId) {

        Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository.findById(butcheryBatchId);

        ButcheryBatchDto butcheryBatchDto = new ButcheryBatchDto();
        butcheryBatchDto.setButcheryBatch(optBatch.get());

        return butcheryBatchDto;
    }

    @Override
    public Page getButcheryBatchInventorySummary(int page, int size, Long vendorWarehouseId, String itemName) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ButcheryBatchInventorySummary> butcheryBatchInventorySummaries =
                this.butcheryBatchInventoryRepository.getInventorySummaryByVendorWarehouse(
                        vendorWarehouseId,
                        itemName,
                        pageable);
        return butcheryBatchInventorySummaries;
    }

    @Override
    @Transactional
    public ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatch butcheryBatch = butcheryBatchDto.getButcheryBatch();

        Optional<VendorWarehouse> vendorWarehouse = this.vendorWarehouseRepository
                .findByVendorWarehouseId(butcheryBatch.getVendorWarehouse().getVendorWarehouseId());
        if (vendorWarehouse.isEmpty()) {
            butcheryBatchDto.setError("Storage provider not found.");
            return butcheryBatchDto;
        }

        Optional<Vendor> vendor = this.vendorRepository.findById(butcheryBatch.getVendor().getVendorId());
        if (vendor.isEmpty()) {
            butcheryBatchDto.setError("Vendor not found");
            return butcheryBatchDto;
        }

        if (butcheryBatch.getButcheryBatchId() > 0) {

            ButcheryBatch optBatch = this.butcheryBatchRepository
                    .findById(butcheryBatchDto.getButcheryBatch().getButcheryBatchId()).get();

            if (optBatch.getBatchStatus().equals("Unposted")) {
                optBatch.setVendorWarehouse(vendorWarehouse.get());
                optBatch.setVendor(vendor.get());
            }

            optBatch.setRemarks(butcheryBatch.getRemarks());
            optBatch.setDateReceived(butcheryBatch.getDateReceived());
            optBatch.setIsOpen(butcheryBatch.getIsOpen());

            butcheryBatchDto.setButcheryBatch(this.butcheryBatchRepository.saveAndFlush(optBatch));

        } else {

            butcheryBatch.setBatchStatus("Unposted");
            butcheryBatch.setHasInventory(false);
            butcheryBatch.setIsOpen(false);
            butcheryBatch.setVendorWarehouse(vendorWarehouse.get());
            butcheryBatch.setVendor(vendor.get());

            Optional<User> user = this.userRepository.findByUsername(butcheryBatch.getCreatedBy().getUsername());
            butcheryBatch.setCreatedBy(user.get());

            butcheryBatch.getButcheryBatchDetails().forEach(batchDetail -> {

                batchDetail.setTotalDocumentedWeightKg(BigDecimal.ZERO);
                batchDetail.setTotalReceivedWeightKg(BigDecimal.ZERO);

                batchDetail.getButcheryBatchDetailItems().forEach(batchDetailItem -> {

                    Optional<Item> optItem = this.itemRepository.findById(batchDetailItem.getItem().getItemId());
                    Optional<Uom> optUom = this.uomRepository.findById(batchDetailItem.getRequiredUom().getUomId());

                    batchDetailItem.setItem(optItem.get());
                    batchDetailItem.setBaseUom(optItem.get().getUom());
                    batchDetailItem.setBaseQty(new BigDecimal(1));
                    batchDetailItem.setRequiredUom(optUom.get());

                    if (batchDetailItem.getBaseUom().getUomId() != batchDetailItem.getRequiredUom().getUomId()) {
                        ItemUomId itemUomId = new ItemUomId();
                        itemUomId.setItemId(optItem.get().getItemId());
                        itemUomId.setUomId(optUom.get().getUomId());

                        Optional<ItemUom> optItemUom = this.itemUomRepository.findById(itemUomId);

                        batchDetailItem.setBaseQty(optItemUom.get().getQuantity());
                    }

                    batchDetail.setTotalDocumentedWeightKg(batchDetail.getTotalDocumentedWeightKg().add(batchDetailItem.getDocumentedWeightKg()));
                    batchDetail.setTotalReceivedWeightKg(batchDetail.getTotalReceivedWeightKg().add(batchDetailItem.getReceivedWeightKg()));

                });

            });

            butcheryBatch = this.butcheryBatchRepository.saveAndFlush(butcheryBatch);

            butcheryBatchDto.setButcheryBatch(butcheryBatch);

        }

        return butcheryBatchDto;
    }

    @Override
    @Transactional
    public ButcheryBatchDto saveButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatch butcheryBatch = butcheryBatchDto.getButcheryBatch();
        ButcheryBatchDetail tmpDetail = butcheryBatchDto.getButcheryBatchDetail();
        ButcheryBatchDetail butcheryBatchDetail;

        if (butcheryBatch.getButcheryBatchId() > 0) {
            Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository.findById(butcheryBatch.getButcheryBatchId());

            if (optBatch.isPresent()) {
                butcheryBatch = optBatch.get();

                if (butcheryBatch.getBatchStatus().equals("Unposted")) {
                    butcheryBatchDetail = butcheryBatchDto.getButcheryBatchDetail();

                    if (tmpDetail.getButcheryBatchDetailId() > 0 ) {
                        Optional<ButcheryBatchDetail> optDetail = this.butcheryBatchDetailRepository.findById(tmpDetail.getButcheryBatchDetailId());

                        if (optDetail.isPresent()) {
                            butcheryBatchDetail = optDetail.get();

                            butcheryBatchDetail.setReferenceNo(tmpDetail.getReferenceNo());

                        }  else {
                            butcheryBatchDto.setError("Batch detail not found.");
                            return butcheryBatchDto;
                        }
                    } else {
                        butcheryBatchDetail.setButcheryBatch(butcheryBatch);
                        butcheryBatchDetail.setTotalDocumentedWeightKg(new BigDecimal(0));
                        butcheryBatchDetail.setTotalReceivedWeightKg(new BigDecimal(0));
                    }

                    butcheryBatchDetail = this.butcheryBatchDetailRepository.saveAndFlush(butcheryBatchDetail);

                    butcheryBatchDto.setButcheryBatchDetail(butcheryBatchDetail);
                } else {
                    butcheryBatchDto.setError("Unable to save information since batch is already " + butcheryBatch.getBatchStatus());
                }
            } else {
                butcheryBatchDto.setError("Batch not found.");
            }
        } else {
            butcheryBatchDto.setError("No batch number.");
        }
        return butcheryBatchDto;
    }

    @Override
    @Transactional
    public ButcheryBatchDto saveButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto) {

        Optional<ButcheryBatchDetail> tmpDetail = this.butcheryBatchDetailRepository.findById(butcheryBatchDto.getButcheryBatchDetail().getButcheryBatchDetailId());

        if (tmpDetail.isPresent()) {

            ButcheryBatchDetail butcheryBatchDetail = tmpDetail.get();

            if (butcheryBatchDetail.getButcheryBatch().getBatchStatus().equals("")) {}

            ButcheryBatchDetailItem tmpDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

            ButcheryBatchDetailItem butcheryBatchDetailItem;

            Optional<Item> optItem = this.itemRepository.findById(tmpDetailItem.getItem().getItemId());
            Optional<Uom> optUom = this.uomRepository.findById(tmpDetailItem.getRequiredUom().getUomId());

            if (tmpDetailItem.getButcheryBatchDetailItemId() > 0) {

                Optional<ButcheryBatchDetailItem> optDetailItem = this.butcheryBatchDetailItemRepository.findById(tmpDetailItem.getButcheryBatchDetailItemId());

                if (optDetailItem.isPresent()) {
                    butcheryBatchDetailItem = optDetailItem.get();

                    butcheryBatchDetailItem.setDocumentedQty(tmpDetailItem.getDocumentedQty());
                    butcheryBatchDetailItem.setReceivedQty(tmpDetailItem.getReceivedQty());
                    butcheryBatchDetailItem.setDocumentedWeightKg(tmpDetailItem.getDocumentedWeightKg());
                    butcheryBatchDetailItem.setReceivedWeightKg(tmpDetailItem.getReceivedWeightKg());
                } else {
                    butcheryBatchDto.setError("Batch detail item not found.");
                    return butcheryBatchDto;
                }
            } else {

                butcheryBatchDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

                butcheryBatchDetailItem.setButcheryBatchDetail(butcheryBatchDetail);

            }

            butcheryBatchDetailItem.setItem(optItem.get());
            butcheryBatchDetailItem.setBaseUom(optItem.get().getUom());
            butcheryBatchDetailItem.setBaseQty(new BigDecimal(1));
            butcheryBatchDetailItem.setRequiredUom(optUom.get());

            if (butcheryBatchDetailItem.getBaseUom().getUomId() != butcheryBatchDetailItem.getRequiredUom().getUomId()) {
                ItemUomId itemUomId = new ItemUomId();
                itemUomId.setItemId(optItem.get().getItemId());
                itemUomId.setUomId(optUom.get().getUomId());

                Optional<ItemUom> optItemUom = this.itemUomRepository.findById(itemUomId);

                butcheryBatchDetailItem.setBaseQty(optItemUom.get().getQuantity());
            }

            butcheryBatchDetailItem = this.butcheryBatchDetailItemRepository.saveAndFlush(butcheryBatchDetailItem);

            Optional<ButcheryBatchDetailItemAggregatedView> optDetailView = this.butcheryBatchDetailItemRepository
                    .getBatchDetailById(butcheryBatchDetail.getButcheryBatchDetailId());

            if (optDetailView.isPresent()) {
                butcheryBatchDetail.setTotalDocumentedWeightKg(optDetailView.get().getTotalDocumentedWeightKg());
                butcheryBatchDetail.setTotalReceivedWeightKg(optDetailView.get().getTotalReceivedWeightKg());
            }

            butcheryBatchDetail = this.butcheryBatchDetailRepository.saveAndFlush(butcheryBatchDetail);

            butcheryBatchDto.setButcheryBatchDetail(butcheryBatchDetail);
            butcheryBatchDto.setButcheryBatchDetailItem(butcheryBatchDetailItem);

        } else {
            butcheryBatchDto.setError("Batch detail not found.");
        }
        return butcheryBatchDto;

    }

    @Override
    @Transactional
    public ButcheryBatchDto deleteButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto) {
        ButcheryBatchDetail tmpDetail = butcheryBatchDto.getButcheryBatchDetail();

        this.butcheryBatchDetailRepository.deleteById(tmpDetail.getButcheryBatchDetailId());

        return butcheryBatchDto;
    }

    @Override
    @Transactional
    public ButcheryBatchDto deleteButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto) {

        Optional<ButcheryBatchDetail> tmpDetail = this.butcheryBatchDetailRepository.findById(butcheryBatchDto.getButcheryBatchDetail().getButcheryBatchDetailId());

        if (tmpDetail.isPresent()) {

            ButcheryBatchDetail butcheryBatchDetail = tmpDetail.get();

            ButcheryBatchDetailItem tmpDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

            this.butcheryBatchDetailItemRepository.deleteById(tmpDetailItem.getButcheryBatchDetailItemId());

            Optional<ButcheryBatchDetailItemAggregatedView> optDetailView = this.butcheryBatchDetailItemRepository
                    .getBatchDetailById(butcheryBatchDetail.getButcheryBatchDetailId());

            if (optDetailView.isPresent()) {
                BigDecimal documentedWeightKg = new BigDecimal(0);
                BigDecimal receivedWeightKg = new BigDecimal(0);

                if (optDetailView.get().getTotalDocumentedWeightKg() != null) {
                    documentedWeightKg.equals(optDetailView.get().getTotalDocumentedWeightKg());
                }

                if (optDetailView.get().getTotalReceivedWeightKg() != null) {
                    receivedWeightKg.equals(optDetailView.get().getTotalReceivedWeightKg());
                }

                butcheryBatchDetail.setTotalDocumentedWeightKg(documentedWeightKg);
                butcheryBatchDetail.setTotalReceivedWeightKg(receivedWeightKg);
            }

            butcheryBatchDetail = this.butcheryBatchDetailRepository.saveAndFlush(butcheryBatchDetail);

            butcheryBatchDto.setButcheryBatchDetail(butcheryBatchDetail);

        }

        return butcheryBatchDto;
    }

    @Override
    @Transactional
    public ButcheryBatchDto updateBatchStatus(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatch tmpBatch = butcheryBatchDto.getButcheryBatch();
        Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository.findById(tmpBatch.getButcheryBatchId());
        if (optBatch.isPresent()) {
            ButcheryBatch butcheryBatch = optBatch.get();

            if (butcheryBatch.getBatchStatus().equals("Unposted")) {
                butcheryBatch.setBatchStatus(tmpBatch.getBatchStatus());
                butcheryBatch.setIsOpen(false);

                if (butcheryBatch.getBatchStatus().equals("Posted")) {
                    butcheryBatch.setIsOpen(true);
                    Set<ButcheryBatchDetailItemInventoryView> inventories =
                            this.butcheryBatchDetailItemRepository.getInventories(butcheryBatch.getButcheryBatchId());

                    inventories.forEach(inv -> {
                        ButcheryBatchInventory butcheryBatchInventory = new ButcheryBatchInventory();

                        butcheryBatchInventory.setButcheryBatch(butcheryBatch);
                        butcheryBatchInventory.setItem(inv.getItem());
                        butcheryBatchInventory.setReceivedQty(inv.getReceivedQty());
                        butcheryBatchInventory.setRemainingQty(inv.getReceivedQty());
                        butcheryBatchInventory.setReceivedWeightKg(inv.getReceivedWeightKg());
                        butcheryBatchInventory.setRemainingWeightKg(inv.getReceivedWeightKg());
                        this.butcheryBatchInventoryRepository.saveAndFlush(butcheryBatchInventory);
                    });
                }

                butcheryBatchDto.setButcheryBatch(this.butcheryBatchRepository.saveAndFlush(butcheryBatch));
            } else {
                butcheryBatchDto.setButcheryBatch(butcheryBatch);
            }
        } else {
            butcheryBatchDto.setError("Batch not found.");
        }
        return butcheryBatchDto;
    }
}
