package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Dto.ButcheryReleasingSummaryDto;
import com.toolkit.inventory.Projection.ButcheryReleasingSummaryView;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryReleasingServiceImp implements ButcheryReleasingService {

    final ButcheryReleasingRepository butcheryReleasingRepository;
    final ButcheryReleasingItemRepository butcheryReleasingItemRepository;
    final ItemRepository itemRepository;
    final ItemUomRepository itemUomRepository;
    final ItemCostRepository itemCostRepository;
    final ItemGenericRepository itemGenericRepository;
    final WarehouseRepository warehouseRepository;
    final CustomerRepository customerRepository;
    final ButcheryProductionItemRepository butcheryProductionItemRepository;

    @Autowired
    public ButcheryReleasingServiceImp(ButcheryReleasingRepository butcheryReleasingRepository,
                                       ButcheryReleasingItemRepository butcheryReleasingItemRepository,
                                       ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                                       ItemCostRepository itemCostRepository,
                                       ItemGenericRepository itemGenericRepository,
                                       WarehouseRepository warehouseRepository,
                                       CustomerRepository customerRepository,
                                       ButcheryProductionItemRepository butcheryProductionItemRepository) {
        this.butcheryReleasingRepository = butcheryReleasingRepository;
        this.butcheryReleasingItemRepository = butcheryReleasingItemRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.itemGenericRepository = itemGenericRepository;
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.butcheryProductionItemRepository = butcheryProductionItemRepository;

    }

    @Override
    public ButcheryReleasingDto getButcheryReleasing(Long butcheryReleasingId) {

        ButcheryReleasingDto butcheryReleasingDto =
                new ButcheryReleasingDto();

        Optional<ButcheryReleasing> optRel
                = this.butcheryReleasingRepository.findById(butcheryReleasingId);

        if (optRel.isPresent()) {
            butcheryReleasingDto.setButcheryReleasingId(butcheryReleasingId);
            butcheryReleasingDto.setWarehouse(optRel.get().getWarehouse());
            butcheryReleasingDto.setDestinationWarehouse(optRel.get().getDestinationWarehouse());
            butcheryReleasingDto.setCustomer(optRel.get().getCustomer());
            butcheryReleasingDto.setTotalAmount(optRel.get().getTotalAmount());
            butcheryReleasingDto.setTotalWeightKg(optRel.get().getTotalWeightKg());
            butcheryReleasingDto.setReleasingStatus(optRel.get().getReleasingStatus());
            butcheryReleasingDto.setDateCreated(optRel.get().getDateCreated());

            Set<ButcheryReleasingItem> butcheryReleasingItems =
                    this.butcheryReleasingItemRepository
                            .findByButcheryReleasingOrderByItemName(optRel.get());

            butcheryReleasingDto.setButcheryReleasingItems(butcheryReleasingItems);
        }

        return butcheryReleasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasing save(ButcheryReleasingDto butcheryReleasingDto) {

        ButcheryReleasing newButcheryReleasing = new ButcheryReleasing();

        newButcheryReleasing.setTotalAmount(butcheryReleasingDto.getTotalAmount());
        newButcheryReleasing.setTotalWeightKg(butcheryReleasingDto.getTotalWeightKg());
        newButcheryReleasing.setReleasingStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryReleasingDto.getWarehouse().getWarehouseId());
        if (optWhse.isPresent()) {
            newButcheryReleasing.setWarehouse(optWhse.get());
        }

        Optional<Warehouse> optDestWhse = this.warehouseRepository.findById(butcheryReleasingDto.getDestinationWarehouse().getWarehouseId());
        if (optDestWhse.isPresent()) {
            newButcheryReleasing.setDestinationWarehouse(optDestWhse.get());
        }

        if (butcheryReleasingDto.getCustomer() != null) {
            Optional<Customer> optCust = this.customerRepository
                    .findById(butcheryReleasingDto.getCustomer().getCustomerId());
            butcheryReleasingDto.setCustomer(optCust.get());
        }

        butcheryReleasingDto.getButcheryReleasingItems().forEach(butcheryReleasingItem -> {

            Item item = null;

            Optional<Item> optItem = this.itemRepository.findById(butcheryReleasingItem.getItem().getItemId());

            if (optItem.isPresent()) {
                item = optItem.get();
            }

            ItemUomId itemUomId = new ItemUomId();

            itemUomId.setItemId(item.getItemId());
            itemUomId.setUomId(butcheryReleasingItem.getRequiredUom().getUomId());

            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

            ButcheryReleasingItem newButcheryReleasingItem =
                    new ButcheryReleasingItem();

            if (itemUom.isPresent()) {

                newButcheryReleasingItem.setBaseQty(itemUom.get().getQuantity());

            } else {

                newButcheryReleasingItem.setBaseQty(new BigDecimal(1));

            }

            newButcheryReleasingItem.setItem(item);
            newButcheryReleasingItem.setCost(BigDecimal.ZERO);
            newButcheryReleasingItem.setBarcode(butcheryReleasingItem.getBarcode());
            newButcheryReleasingItem.setItemClass(item.getItemClass());
            newButcheryReleasingItem.setBaseUom(item.getUom());
            newButcheryReleasingItem.setRequiredUom(butcheryReleasingItem.getRequiredUom());
            newButcheryReleasingItem.setReleasedQty(butcheryReleasingItem.getReleasedQty());
            newButcheryReleasingItem.setReleasedWeightKg(butcheryReleasingItem.getReleasedWeightKg());
            newButcheryReleasingItem.setItemPrice(butcheryReleasingItem.getItemPrice());
            newButcheryReleasingItem.setTotalAmount(butcheryReleasingItem.getTotalAmount());

            newButcheryReleasing.addButcheryReleasingItem(newButcheryReleasingItem);

        });

        this.butcheryReleasingRepository.save(newButcheryReleasing);

        return newButcheryReleasing;
    }

    @Override
    @Transactional
    public ButcheryReleasingDto setButcheryReleasing(ButcheryReleasingDto butcheryReleasingDto) {

        ButcheryReleasingDto newButcheryReleasingDto = new ButcheryReleasingDto();

        Long butcheryReleasingId = butcheryReleasingDto.getButcheryReleasingId();

        newButcheryReleasingDto.setButcheryReleasingId(butcheryReleasingId);

        Optional<ButcheryReleasing> optProd =
                this.butcheryReleasingRepository.findByButcheryReleasingId(butcheryReleasingId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            newButcheryReleasingDto.setReleasingStatus(butcheryReleasing.getReleasingStatus());

            if (butcheryReleasing.getReleasingStatus().equals("Unposted")) {

                if (butcheryReleasingDto.getWarehouse() != null) {
                    Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryReleasingDto.getWarehouse().getWarehouseId());
                    butcheryReleasing.setWarehouse(optWhse.get());
                }

                if (butcheryReleasingDto.getDestinationWarehouse() != null) {
                    Optional<Warehouse> optDestWhse = this.warehouseRepository.findById(butcheryReleasingDto.getDestinationWarehouse().getWarehouseId());
                    butcheryReleasing.setDestinationWarehouse(optDestWhse.get());
                }

                if (butcheryReleasingDto.getCustomer() != null) {
                    Optional<Customer> optCust = this.customerRepository.findById(butcheryReleasingDto.getCustomer().getCustomerId());
                    butcheryReleasing.setCustomer(optCust.get());
                }

                this.butcheryReleasingRepository.save(butcheryReleasing);

            } else {
                newButcheryReleasingDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        butcheryReleasing.getReleasingStatus());
            }

        }

        return newButcheryReleasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasingDto setReleasingStatus(ButcheryReleasingDto dto) {

        Long relId = dto.getButcheryReleasingId();

        ButcheryReleasingDto  newButcheryReleasingDto = new ButcheryReleasingDto();

        newButcheryReleasingDto.setButcheryReleasingId(relId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository.findByButcheryReleasingId(relId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            String oldStatus = butcheryReleasing.getReleasingStatus();
            String newStatus = dto.getReleasingStatus();

            newButcheryReleasingDto.setReleasingStatus(oldStatus);

            if (oldStatus.equals("Unposted")) {
                butcheryReleasing.setReleasingStatus(newStatus);

                if (newStatus.equals("Posted")) {
                    Warehouse warehouse = butcheryReleasing.getWarehouse();

                    Set<ButcheryReleasingItem> butcheryReleasingItems =
                            this.butcheryReleasingItemRepository
                                    .findByButcheryReleasingOrderByItemName(butcheryReleasing);

                    butcheryReleasingItems.forEach(butcheryReleasingItem -> {

                        Optional<ItemCost> optItemCost = this.itemCostRepository
                                .findByItemAndWarehouse(butcheryReleasingItem.getItem(), warehouse);
                        if (optItemCost.isPresent()) {

                            ItemCost itemCost = optItemCost.get();

                            butcheryReleasingItem.setCost(itemCost.getCost());

                            BigDecimal baseQty = butcheryReleasingItem.getBaseQty();
                            BigDecimal releasedQty = butcheryReleasingItem.getReleasedQty();
                            BigDecimal releasedWeightKg = butcheryReleasingItem.getReleasedWeightKg().multiply(new BigDecimal(-1));
                            BigDecimal ttlQty =  releasedQty.multiply(baseQty)
                                                            .multiply(new BigDecimal(-1));

                            this.butcheryReleasingItemRepository.save(butcheryReleasingItem);
                            this.itemCostRepository.setQty(ttlQty, releasedWeightKg, itemCost.getItemCostId());

                        }

                    });

                }
                this.butcheryReleasingRepository.save(butcheryReleasing);
            } else {
                newButcheryReleasingDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        oldStatus);
            }

        }

        return newButcheryReleasingDto;
    }

    @Override
    @Transactional
    public ButcheryReleasingDto putButcheryReleasingItem(
            ButcheryReleasingItem butcheryReleasingItem) {

        ButcheryReleasingDto releasingDto = new ButcheryReleasingDto();

        Long releasingId = butcheryReleasingItem
                .getButcheryReleasing().getButcheryReleasingId();

        releasingDto.setButcheryReleasingId(releasingId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository
                .findByButcheryReleasingId(releasingId);

        if (optProd.isPresent()) {

            ButcheryReleasing butcheryReleasing = optProd.get();

            releasingDto.setReleasingStatus(butcheryReleasing.getReleasingStatus());

            if (butcheryReleasing.getReleasingStatus().equals("Unposted")) {

                ButcheryReleasingItem newReleasingItem = new ButcheryReleasingItem();

                newReleasingItem.setButcheryReleasing(butcheryReleasing);

                if (butcheryReleasingItem.getButcheryReleasingItemId() != null) {
                    newReleasingItem.setButcheryReleasingItemId(
                            butcheryReleasingItem.getButcheryReleasingItemId());
                }

                Item item = null;

                Optional<Item> tmpItem = this.itemRepository.findById(
                        butcheryReleasingItem.getItem().getItemId());

                if (tmpItem.isPresent()) {
                    item = tmpItem.get();
                }

                ItemUomId itemUomId = new ItemUomId();

                itemUomId.setItemId(item.getItemId());
                itemUomId.setUomId(butcheryReleasingItem.getRequiredUom().getUomId());

                Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);
                if (itemUom.isPresent()) {
                    newReleasingItem.setBaseQty(itemUom.get().getQuantity());
                } else {
                    newReleasingItem.setBaseQty(new BigDecimal(1));
                }

                newReleasingItem.setItem(item);
                newReleasingItem.setCost(BigDecimal.ZERO);
                newReleasingItem.setBarcode(butcheryReleasingItem.getBarcode());
                newReleasingItem.setBaseUom(item.getUom());
                newReleasingItem.setItemClass(item.getItemClass());
                newReleasingItem.setRequiredUom(butcheryReleasingItem.getRequiredUom());
                newReleasingItem.setReleasedQty(butcheryReleasingItem.getReleasedQty());
                newReleasingItem.setReleasedWeightKg(butcheryReleasingItem.getReleasedWeightKg());
                newReleasingItem.setItemPrice(butcheryReleasingItem.getItemPrice());
                newReleasingItem.setTotalAmount((butcheryReleasingItem.getTotalAmount()));

                this.butcheryReleasingItemRepository.save(newReleasingItem);

                butcheryReleasing.setTotalAmount(this.butcheryReleasingRepository.getTotalAmount(butcheryReleasing));
                butcheryReleasing.setTotalWeightKg(this.butcheryReleasingRepository.getTotalWeightKg(butcheryReleasing));

                this.butcheryReleasingRepository.save(butcheryReleasing);

                releasingDto.setButcheryReleasingItem(newReleasingItem);

            } else {
                releasingDto.setErrorDescription("Unable to process your request since this transaction has already been tagged as " +
                        butcheryReleasing.getReleasingStatus());
            }

        }
        return releasingDto;

    }

    @Override
    @Transactional
    public ButcheryReleasingDto deleteButcheryReleasingItem(
            ButcheryReleasingItem butcheryReleasingItem) {

        ButcheryReleasingDto butcheryReleasingDto =
                new ButcheryReleasingDto();

        Long releasingItemId = butcheryReleasingItem.getButcheryReleasingItemId();

        Long releasingId = butcheryReleasingItem.getButcheryReleasing().getButcheryReleasingId();

        butcheryReleasingDto.setButcheryReleasingId(releasingId);

        Optional<ButcheryReleasing> optProd = this.butcheryReleasingRepository.findByButcheryReleasingId(releasingId);
        if (optProd.isPresent()) {
            ButcheryReleasing releasing = optProd.get();

            butcheryReleasingDto.setReleasingStatus(releasing.getReleasingStatus());

            if (releasing.getReleasingStatus().equals("Unposted")) {

                this.butcheryReleasingItemRepository.deleteById(releasingItemId);

                BigDecimal totalAmount = this.butcheryReleasingRepository.getTotalAmount(releasing);
                if (totalAmount != null) {
                    releasing.setTotalAmount(totalAmount);
                } else {
                    releasing.setTotalAmount(BigDecimal.ZERO);
                }

                BigDecimal totalWeightKg = this.butcheryReleasingRepository.getTotalWeightKg(releasing);
                if (totalWeightKg != null) {
                    releasing.setTotalWeightKg(totalWeightKg);
                } else {
                    releasing.setTotalWeightKg(BigDecimal.ZERO);
                }

            } else {
                butcheryReleasingDto.setErrorDescription("Unable to process your request since " +
                        "this transaction has already been tagged as " +
                        releasing.getReleasingStatus());
            }
        }

        return butcheryReleasingDto;
    }

    @Override
    public Set<ButcheryReleasingItem> getButcheryReleasingItems(Long butcheryReleasingId) {

        return this.butcheryReleasingItemRepository.findByButcheryReleasingIdOrderByItemName(butcheryReleasingId);

    }

    @Override
    public Set<ButcheryReleasingSummaryView> getButcheryReleasingSummary(Long warehouseId) {

        return this.butcheryReleasingRepository.getButcheryReceivingSummaryByWarehouseId(warehouseId);

    }
}
