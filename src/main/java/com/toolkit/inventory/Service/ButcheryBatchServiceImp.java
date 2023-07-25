package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Repository.*;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ButcheryBatchServiceImp implements ButcheryBatchService{

    final ButcheryBatchRepository butcheryBatchRepository;
    final ButcheryBatchDetailRepository butcheryBatchDetailRepository;
    final ButcheryBatchDetailItemRepository butcheryBatchDetailItemRepository;
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
    @Transactional
    public ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatch butcheryBatch = butcheryBatchDto.getButcheryBatch();

        if (butcheryBatch.getButcheryBatchId() > 0) {

            ButcheryBatch optBatch = this.butcheryBatchRepository
                    .findById(butcheryBatchDto.getButcheryBatch().getButcheryBatchId()).get();

            optBatch.setVendorWarehouse(butcheryBatch.getVendorWarehouse());
            optBatch.setRemarks(butcheryBatch.getRemarks());
            optBatch.setDateReceived(butcheryBatch.getDateReceived());
            optBatch.setIsOpen(butcheryBatch.getIsOpen());

            this.butcheryBatchRepository.saveAndFlush(optBatch);

            butcheryBatch.setCreatedBy(null);

        } else {

            butcheryBatch.setBatchStatus("Unposted");
            butcheryBatch.setHasInventory(false);
            butcheryBatch.setIsOpen(false);

            Optional<User> user = this.userRepository.findByUsername(butcheryBatch.getCreatedBy().getUsername());
            butcheryBatch.setCreatedBy(user.get());

            Optional<VendorWarehouse> vendorWarehouse = this.vendorWarehouseRepository
                    .findByVendorWarehouseId(butcheryBatch.getVendorWarehouse().getVendorWarehouseId());

            butcheryBatch.setVendorWarehouse(vendorWarehouse.get());

            if (butcheryBatch.getButcheryBatchId() == 0) {
                butcheryBatch.getButcheryBatchDetails().forEach(batchDetail -> {

                    Optional<Vendor> optVendor = this.vendorRepository.findById(batchDetail.getVendor().getVendorId());

                    batchDetail.setVendor(optVendor.get());

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

                    });
                });
            }

            butcheryBatch = this.butcheryBatchRepository.saveAndFlush(butcheryBatch);

            butcheryBatchDto.setButcheryBatch(butcheryBatch);

        }

        return butcheryBatchDto;
    }

    @Override
    @Transactional
    public ButcheryBatchDto saveButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatchDetail tmpDetail = butcheryBatchDto.getButcheryBatchDetail();

        ButcheryBatchDetail butcheryBatchDetail;

        Optional<Vendor> optVendor = this.vendorRepository.findById(tmpDetail.getVendor().getVendorId());

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

            ButcheryBatch butcheryBatch = butcheryBatchDto.getButcheryBatch();

            if (butcheryBatch.getButcheryBatchId() > 0) {

                Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository.findById(butcheryBatch.getButcheryBatchId());

                if (optBatch.isPresent()) {

                    butcheryBatch = optBatch.get();

                    butcheryBatchDetail = butcheryBatchDto.getButcheryBatchDetail();

                    butcheryBatchDetail.setButcheryBatch(butcheryBatch);

                } else {
                    butcheryBatchDto.setError("Batch not found.");
                    return butcheryBatchDto;
                }
            } else {
                butcheryBatchDto.setError("No batch number.");
                return butcheryBatchDto;
            }
        }

        butcheryBatchDetail.setVendor(optVendor.get());

        butcheryBatchDetail = this.butcheryBatchDetailRepository.saveAndFlush(butcheryBatchDetail);

        butcheryBatchDto.setButcheryBatchDetail(butcheryBatchDetail);

        return butcheryBatchDto;

    }

    @Override
    @Transactional
    public ButcheryBatchDto saveButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatchDetailItem tmpDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

        ButcheryBatchDetailItem butcheryBatchDetailItem;

        Optional<Item> optItem = this.itemRepository.findById(tmpDetailItem.getItem().getItemId());
        Optional<Uom> optUom = this.uomRepository.findById(tmpDetailItem.getRequiredUom().getUomId());

        if (tmpDetailItem.getButcheryBatchDetailItemId() > 0) {

            Optional<ButcheryBatchDetailItem> optDetailItem = this.butcheryBatchDetailItemRepository.findById(tmpDetailItem.getButcheryBatchDetailItemId());

            if (optDetailItem.isPresent()) {
                butcheryBatchDetailItem = optDetailItem.get();

                butcheryBatchDetailItem.setRequiredQty(tmpDetailItem.getRequiredQty());
                butcheryBatchDetailItem.setReceivedQty(tmpDetailItem.getReceivedQty());
                butcheryBatchDetailItem.setRequiredWeightKg(tmpDetailItem.getRequiredWeightKg());
                butcheryBatchDetailItem.setReceivedWeightKg(tmpDetailItem.getReceivedWeightKg());
            } else {
                butcheryBatchDto.setError("Batch detail item not found.");
                return butcheryBatchDto;
            }
        } else {

            ButcheryBatchDetail butcheryBatchDetail = butcheryBatchDto.getButcheryBatchDetail();

            if (butcheryBatchDetail.getButcheryBatchDetailId() > 0) {

                Optional<ButcheryBatchDetail> optBatchDetail = this.butcheryBatchDetailRepository.findById(butcheryBatchDetail.getButcheryBatchDetailId());

                if (optBatchDetail.isPresent()) {
                    butcheryBatchDetail = optBatchDetail.get();

                    butcheryBatchDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

                    butcheryBatchDetailItem.setButcheryBatchDetail(butcheryBatchDetail);
                } else {
                    butcheryBatchDto.setError("Batch detail not found.");
                    return butcheryBatchDto;
                }
            } else {
                butcheryBatchDto.setError("No batch detail number.");
                return butcheryBatchDto;
            }
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

        butcheryBatchDto.setButcheryBatchDetailItem(butcheryBatchDetailItem);

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
        ButcheryBatchDetailItem tmpDetailItem = butcheryBatchDto.getButcheryBatchDetailItem();

        this.butcheryBatchDetailItemRepository.deleteById(tmpDetailItem.getButcheryBatchDetailItemId());

        return butcheryBatchDto;
    }
}
