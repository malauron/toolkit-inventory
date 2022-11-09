package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryReceivingDto;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryReceivingServiceImp implements ButcheryReceivingService {

    private ButcheryReceivingRepository butcheryReceivingRepository;

    private ButcheryReceivingItemRepository butcheryReceivingItemRepository;

    private ItemRepository itemRepository;

    private ItemUomRepository itemUomRepository;

    private ItemCostRepository itemCostRepository;

    private WarehouseRepository warehouseRepository;

    private VendorRepository customerRepository;

    @Autowired
    public ButcheryReceivingServiceImp(ButcheryReceivingRepository butcheryReceivingRepository,
                                       ButcheryReceivingItemRepository butcheryReceivingItemRepository,
                                       ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                                       ItemCostRepository itemCostRepository,
                                       WarehouseRepository warehouseRepository,
                                       VendorRepository customerRepository) {
        this.butcheryReceivingRepository = butcheryReceivingRepository;
        this.butcheryReceivingItemRepository = butcheryReceivingItemRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;

    }

    @Override
    public ButcheryReceivingDto getButcheryReceiving(Long butcheryReceivingId) {

        ButcheryReceivingDto butcheryReceivingDto =
                new ButcheryReceivingDto();

        Optional<ButcheryReceiving> optRecv
                = this.butcheryReceivingRepository.findById(butcheryReceivingId);

        if (optRecv.isPresent()) {
            butcheryReceivingDto.setButcheryReceivingId(butcheryReceivingId);
            butcheryReceivingDto.setWarehouse(optRecv.get().getWarehouse());
            butcheryReceivingDto.setVendor(optRecv.get().getVendor());
            butcheryReceivingDto.setReferenceCode(optRecv.get().getReferenceCode());
            butcheryReceivingDto.setTotalAmount(optRecv.get().getTotalAmount());
            butcheryReceivingDto.setReceivingStatus(optRecv.get().getReceivingStatus());
            butcheryReceivingDto.setDateCreated(optRecv.get().getDateCreated());

            Set<ButcheryReceivingItem> butcheryReceivingItems =
                    this.butcheryReceivingItemRepository
                            .findByButcheryReceivingOrderByItemName(optRecv.get());

            butcheryReceivingDto.setButcheryReceivingItems(butcheryReceivingItems);
        }

        return butcheryReceivingDto;

    }

    @Override
    public Optional<ButcheryReceivingItemView> getButcheryReceivingItem(Long butcheryReceivingItemId) {
        return this.butcheryReceivingItemRepository.findByIdAndIsAvailable(butcheryReceivingItemId);
    }

    @Override
    public Set<ButcheryReceivingItemView> getButcheryReceivingItemsByWarehouseId(Long warehouseId) {
        Optional<Warehouse> opt = this.warehouseRepository.findById(warehouseId);

        return this.butcheryReceivingItemRepository.findByWarehouseAndIsAvailable(opt.get());
    }

    @Override
    @Transactional
    public ButcheryReceiving save(ButcheryReceivingDto butcheryReceivingDto) {

        ButcheryReceiving newButcheryReceiving = new ButcheryReceiving();

        newButcheryReceiving.setReferenceCode(butcheryReceivingDto.getReferenceCode());
        newButcheryReceiving.setTotalAmount(butcheryReceivingDto.getTotalAmount());
        newButcheryReceiving.setReceivingStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryReceivingDto.getWarehouse().getWarehouseId());

        if (optWhse.isPresent()) {
            newButcheryReceiving.setWarehouse(optWhse.get());
        }

        Optional<Vendor> optVend = this.customerRepository.findById(butcheryReceivingDto.getVendor().getVendorId());

        if (optVend.isPresent()) {
            newButcheryReceiving.setVendor(optVend.get());
        }

        butcheryReceivingDto.getButcheryReceivingItems().forEach(butcheryReceivingItem -> {

            Item item = null;

            Optional<Item> optItem = this.itemRepository.findById(butcheryReceivingItem.getItem().getItemId());

            if (optItem.isPresent()) {
                item = optItem.get();
            }

            ItemUomId itemUomId = new ItemUomId();

            itemUomId.setItemId(item.getItemId());
            itemUomId.setUomId(butcheryReceivingItem.getRequiredUom().getUomId());

            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

            ButcheryReceivingItem newButcheryReceivingItem =
                    new ButcheryReceivingItem();

            if (itemUom.isPresent()) {

                newButcheryReceivingItem.setBaseQty(itemUom.get().getQuantity());

            } else {

                newButcheryReceivingItem.setBaseQty(new BigDecimal(1));

            }

            newButcheryReceivingItem.setItem(item);
            newButcheryReceivingItem.setBarcode(butcheryReceivingItem.getBarcode());
            newButcheryReceivingItem.setItemClass(item.getItemClass());
            newButcheryReceivingItem.setBaseUom(item.getUom());
            newButcheryReceivingItem.setRequiredUom(butcheryReceivingItem.getRequiredUom());
            newButcheryReceivingItem.setReceivedQty(butcheryReceivingItem.getReceivedQty());
            newButcheryReceivingItem.setItemCost(butcheryReceivingItem.getItemCost());
            newButcheryReceivingItem.setDocumentedQty(butcheryReceivingItem.getDocumentedQty());
            newButcheryReceivingItem.setUsedQty(BigDecimal.ZERO);
            newButcheryReceivingItem.setRemarks(butcheryReceivingItem.getRemarks());
            newButcheryReceivingItem.setIsAvailable(false);
            newButcheryReceivingItem.setTotalAmount(butcheryReceivingItem.getTotalAmount());

            newButcheryReceiving.addButcheryReceivingItem(newButcheryReceivingItem);

        });

        this.butcheryReceivingRepository.save(newButcheryReceiving);

        return newButcheryReceiving;
    }

    @Override
    @Transactional
    public ButcheryReceivingDto setButcheryReceiving(ButcheryReceivingDto butcheryReceivingDto) {

        ButcheryReceivingDto newButcheryReceivingDto = new ButcheryReceivingDto();

        Long butcheryReceivingId = butcheryReceivingDto.getButcheryReceivingId();

        newButcheryReceivingDto.setButcheryReceivingId(butcheryReceivingId);

        Optional<ButcheryReceiving> optRecv =
                this.butcheryReceivingRepository.findByButcheryReceivingId(butcheryReceivingId);

        if (optRecv.isPresent()) {

            ButcheryReceiving butcheryReceiving = optRecv.get();

            newButcheryReceivingDto.setReceivingStatus(butcheryReceiving.getReceivingStatus());

            if (butcheryReceiving.getReceivingStatus().equals("Unposted")) {
                if (butcheryReceivingDto.getWarehouse() != null) {
                    butcheryReceiving.setWarehouse(butcheryReceivingDto.getWarehouse());
                }

                if (butcheryReceivingDto.getVendor() != null) {
                    butcheryReceiving.setVendor(butcheryReceivingDto.getVendor());
                }

                if (butcheryReceivingDto.getReferenceCode() != null) {
                    butcheryReceiving.setReferenceCode(butcheryReceivingDto.getReferenceCode());
                }
            }

            this.butcheryReceivingRepository.save(butcheryReceiving);

        }

        return newButcheryReceivingDto;

    }

    @Override
    @Transactional
    public ButcheryReceivingDto setReceivingStatus(ButcheryReceivingDto dto) {

        Long relId = dto.getButcheryReceivingId();

        ButcheryReceivingDto  newButcheryReceivingDto = new ButcheryReceivingDto();

        newButcheryReceivingDto.setButcheryReceivingId(relId);

        Optional<ButcheryReceiving> optRecv = this.butcheryReceivingRepository.findByButcheryReceivingId(relId);

        if (optRecv.isPresent()) {

            ButcheryReceiving butcheryReceiving = optRecv.get();

            String oldStatus = butcheryReceiving.getReceivingStatus();
            String newStatus = dto.getReceivingStatus();

            newButcheryReceivingDto.setReceivingStatus(oldStatus);

            if (oldStatus.equals("Unposted")) {

                butcheryReceiving.setReceivingStatus(newStatus);

                if (newStatus.equals("Posted")) {

                    Warehouse warehouse = butcheryReceiving.getWarehouse();

                    Set<ButcheryReceivingItem> butcheryReceivingItems =
                            this.butcheryReceivingItemRepository
                                    .findByButcheryReceivingOrderByItemName(butcheryReceiving);

                    butcheryReceivingItems.forEach(butcheryReceivingItem -> {

                        butcheryReceivingItem.setIsAvailable(true);

                        Item item = butcheryReceivingItem.getItem();
                        BigDecimal baseQty = butcheryReceivingItem.getBaseQty();
                        BigDecimal receivedQty = butcheryReceivingItem.getReceivedQty();
                        BigDecimal itemCost = butcheryReceivingItem.getItemCost();
                        BigDecimal ttQty =  receivedQty.multiply(baseQty);
                        BigDecimal cost = itemCost.divide(baseQty);

                        this.itemCostRepository.setQtyCost(ttQty, cost, item, warehouse);

                        this.butcheryReceivingItemRepository.save(butcheryReceivingItem);

                    });

                }

                this.butcheryReceivingRepository.save(butcheryReceiving);

            }

        }

        return newButcheryReceivingDto;
    }

    @Override
    @Transactional
    public ButcheryReceivingDto putButcheryReceivingItem(
            ButcheryReceivingItem butcheryReceivingItem) {

        ButcheryReceivingDto receivingDto = new ButcheryReceivingDto();

        Long receivingId = butcheryReceivingItem
                .getButcheryReceiving().getButcheryReceivingId();

        receivingDto.setButcheryReceivingId(receivingId);

        Optional<ButcheryReceiving> optRecv = this.butcheryReceivingRepository
                .findByButcheryReceivingId(receivingId);

        if (optRecv.isPresent()) {

            ButcheryReceiving butcheryReceiving = optRecv.get();

            receivingDto.setReceivingStatus(butcheryReceiving.getReceivingStatus());

            ButcheryReceivingItem newReceivingItem = new ButcheryReceivingItem();

            newReceivingItem.setButcheryReceiving(butcheryReceiving);

            if (butcheryReceiving.getReceivingStatus().equals("Unposted")) {

                if (butcheryReceivingItem.getButcheryReceivingItemId() != null) {

                    newReceivingItem.setButcheryReceivingItemId(
                            butcheryReceivingItem.getButcheryReceivingItemId());

                }

                Item item = null;

                Optional<Item> tmpItem = this.itemRepository.findById(
                        butcheryReceivingItem.getItem().getItemId());

                if (tmpItem.isPresent()) {
                    item = tmpItem.get();
                }

                ItemUomId itemUomId = new ItemUomId();

                itemUomId.setItemId(item.getItemId());
                itemUomId.setUomId(butcheryReceivingItem.getRequiredUom().getUomId());

                Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

                if (itemUom.isPresent()) {

                    newReceivingItem.setBaseQty(itemUom.get().getQuantity());

                } else {

                    newReceivingItem.setBaseQty(new BigDecimal(1));

                }

                newReceivingItem.setItem(item);
                newReceivingItem.setBarcode(butcheryReceivingItem.getBarcode());
                newReceivingItem.setBaseUom(item.getUom());
                newReceivingItem.setItemClass(item.getItemClass());
                newReceivingItem.setRequiredUom(butcheryReceivingItem.getRequiredUom());
                newReceivingItem.setReceivedQty(butcheryReceivingItem.getReceivedQty());
                newReceivingItem.setItemCost(butcheryReceivingItem.getItemCost());
                newReceivingItem.setDocumentedQty(butcheryReceivingItem.getDocumentedQty());
                newReceivingItem.setUsedQty(butcheryReceivingItem.getUsedQty());
                newReceivingItem.setRemarks(butcheryReceivingItem.getRemarks());
                newReceivingItem.setIsAvailable(butcheryReceivingItem.getIsAvailable());
                newReceivingItem.setTotalAmount((butcheryReceivingItem.getTotalAmount()));

                this.butcheryReceivingItemRepository.save(newReceivingItem);

                butcheryReceiving.setTotalAmount(this.butcheryReceivingRepository.getTotalAmount(butcheryReceiving));

                this.butcheryReceivingRepository.save(butcheryReceiving);

                receivingDto.setButcheryReceivingItem(newReceivingItem);

            }

        }

        return receivingDto;

    }

    @Override
    @Transactional
    public ButcheryReceivingDto deleteButcheryReceivingItem(
            ButcheryReceivingItem butcheryReceivingItem) {

        ButcheryReceivingDto butcheryReceivingDto =
                new ButcheryReceivingDto();

        Long receivingItemId = butcheryReceivingItem.getButcheryReceivingItemId();

        Long receivingId = butcheryReceivingItem.getButcheryReceiving().getButcheryReceivingId();

        butcheryReceivingDto.setButcheryReceivingId(receivingId);

        Optional<ButcheryReceiving> optRecv = this.butcheryReceivingRepository.findByButcheryReceivingId(receivingId);

        if (optRecv.isPresent()) {
            ButcheryReceiving receiving = optRecv.get();

            butcheryReceivingDto.setReceivingStatus(receiving.getReceivingStatus());

            if (receiving.getReceivingStatus().equals("Unposted")) {

                this.butcheryReceivingItemRepository.deleteById(receivingItemId);

                receiving.setTotalAmount(this.butcheryReceivingRepository
                        .getTotalAmount(receiving));
            }
        }

        return butcheryReceivingDto;
    }

    @Override
    @Transactional
    public Optional<ButcheryReceivingItem> findById(Long butcheryReceivingItemId) {
        return this.butcheryReceivingItemRepository.findById(butcheryReceivingItemId);
    }
}
