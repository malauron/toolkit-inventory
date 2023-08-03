package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryProductionDto;
import com.toolkit.inventory.Projection.*;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryProductionServiceImp implements ButcheryProductionService {

    final ButcheryProductionRepository butcheryProductionRepository;
    final ButcheryProductionItemRepository butcheryProductionItemRepository;
    final ButcheryProductionSourceRepository butcheryProductionSourceRepository;
    final ButcheryReceivingRepository butcheryReceivingRepository;
    final ButcheryReceivingItemRepository butcheryReceivingItemRepository;
    final ButcheryBatchRepository butcheryBatchRepository;
    final ItemRepository itemRepository;
    final ItemUomRepository itemUomRepository;
    final ItemCostRepository itemCostRepository;
    final WarehouseRepository warehouseRepository;

    @Autowired
    public ButcheryProductionServiceImp(ButcheryProductionRepository butcheryProductionRepository,
                                        ButcheryProductionItemRepository butcheryProductionItemRepository,
                                        ButcheryProductionSourceRepository butcheryProductionSourceRepository,
                                        ButcheryReceivingRepository butcheryReceivingRepository,
                                        ButcheryReceivingItemRepository butcheryReceivingItemRepository,
                                        ButcheryBatchRepository butcheryBatchRepository,
                                        ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                                        ItemCostRepository itemCostRepository,
                                        WarehouseRepository warehouseRepository) {
        this.butcheryProductionRepository = butcheryProductionRepository;
        this.butcheryProductionItemRepository = butcheryProductionItemRepository;
        this.butcheryProductionSourceRepository = butcheryProductionSourceRepository;
        this.butcheryReceivingRepository = butcheryReceivingRepository;
        this.butcheryReceivingItemRepository = butcheryReceivingItemRepository;
        this.butcheryBatchRepository = butcheryBatchRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ButcheryProductionDto getButcheryProduction(Long butcheryProductionId) {

        ButcheryProductionDto butcheryProductionDto =
                new ButcheryProductionDto();

        Optional<ButcheryProduction> optProd
                = this.butcheryProductionRepository.findById(butcheryProductionId);

        if (optProd.isPresent()) {
            butcheryProductionDto.setButcheryProductionId(butcheryProductionId);
            butcheryProductionDto.setWarehouse(optProd.get().getWarehouse());
            butcheryProductionDto.setButcheryBatch(optProd.get().getButcheryBatch());
            butcheryProductionDto.setTotalAmount(optProd.get().getTotalAmount());
            butcheryProductionDto.setProductionStatus(optProd.get().getProductionStatus());
            butcheryProductionDto.setDateCreated(optProd.get().getDateCreated());

            Set<ButcheryProductionSourceView> butcheryProductionSources =
                    this.butcheryProductionSourceRepository
                            .findByButcheryProductionOrderByButcheryReceivingItemIdView(optProd.get());

            butcheryProductionDto.setButcheryProductionSourceViews(butcheryProductionSources);

//            Set<ButcheryProductionSource> butcheryProductionSources =
//                    this.butcheryProductionSourceRepository
//                            .findByButcheryProductionOrderByButcheryReceivingItemId(optProd.get());
//
//            butcheryProductionDto.setButcheryProductionSources(butcheryProductionSources);

            Set<ButcheryProductionItem> butcheryProductionItems =
                    this.butcheryProductionItemRepository
                            .findByButcheryProductionOrderByItemName(optProd.get());

            butcheryProductionDto.setButcheryProductionItems(butcheryProductionItems);
        }

        return butcheryProductionDto;

    }

    @Override
    @Transactional
    public ButcheryProductionDto save(ButcheryProductionDto butcheryProductionDto) {

        ButcheryProduction newButcheryProduction = new ButcheryProduction();

        newButcheryProduction.setTotalAmount(butcheryProductionDto.getTotalAmount());
        newButcheryProduction.setProductionStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryProductionDto.getWarehouse().getWarehouseId());

        if (optWhse.isPresent()) {
            newButcheryProduction.setWarehouse(optWhse.get());
        }

        Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository.findById(butcheryProductionDto.getButcheryBatch().getButcheryBatchId());

        if (optBatch.isPresent()) {
            newButcheryProduction.setButcheryBatch(optBatch.get());
        }

        butcheryProductionDto.getButcheryProductionSources().forEach(butcheryProductionSource -> {

            ButcheryProductionSource newButcheryProductionSource =
                    new ButcheryProductionSource();

            Optional<ButcheryReceivingItem> optButRec = this.butcheryReceivingItemRepository
                    .findById(butcheryProductionSource.getButcheryReceivingItem().getButcheryReceivingItemId());

            if (optButRec.isPresent()) {
                newButcheryProductionSource.setButcheryReceivingItem(optButRec.get());
            }
            newButcheryProductionSource.setRequiredQty(butcheryProductionSource.getRequiredQty());
            newButcheryProduction.addButcheryProductionSource(newButcheryProductionSource);

        });

        butcheryProductionDto.getButcheryProductionItems().forEach(butcheryProductionItem -> {

            Item item = null;

            Optional<Item> optItem = this.itemRepository.findById(butcheryProductionItem.getItem().getItemId());

            if (optItem.isPresent()) {
                item = optItem.get();
            }

            ItemUomId itemUomId = new ItemUomId();

            itemUomId.setItemId(item.getItemId());
            itemUomId.setUomId(butcheryProductionItem.getRequiredUom().getUomId());

            Optional<ItemUom> itemUom = itemUomRepository.findById(itemUomId);

            ButcheryProductionItem newButcheryProductionItem =
                    new ButcheryProductionItem();

            if (itemUom.isPresent()) {

                newButcheryProductionItem.setBaseQty(itemUom.get().getQuantity());

            } else {

                newButcheryProductionItem.setBaseQty(new BigDecimal(1));

            }

            newButcheryProductionItem.setItem(item);
            newButcheryProductionItem.setBarcode(butcheryProductionItem.getBarcode());
            newButcheryProductionItem.setItemClass(item.getItemClass());
            newButcheryProductionItem.setBaseUom(item.getUom());
            newButcheryProductionItem.setRequiredUom(butcheryProductionItem.getRequiredUom());
            newButcheryProductionItem.setProducedQty(butcheryProductionItem.getProducedQty());
            newButcheryProductionItem.setProductionCost(butcheryProductionItem.getProductionCost());
            newButcheryProductionItem.setTotalAmount(butcheryProductionItem.getTotalAmount());
            newButcheryProductionItem.setWarehouse(newButcheryProduction.getWarehouse());
            newButcheryProductionItem.setIsAvailable(false);

            newButcheryProduction.addButcheryProductionItem(newButcheryProductionItem);

        });

        this.butcheryProductionRepository.save(newButcheryProduction);

        ButcheryProductionDto newButcheryProductionDto = new ButcheryProductionDto();

        newButcheryProductionDto.setButcheryProductionId(newButcheryProduction.getButcheryProductionId());
        newButcheryProductionDto.setProductionStatus(newButcheryProduction.getProductionStatus());
        newButcheryProductionDto.setTotalAmount(newButcheryProduction.getTotalAmount());
        newButcheryProductionDto.setDateCreated(newButcheryProduction.getDateCreated());
        newButcheryProductionDto.setButcheryProductionItems(newButcheryProduction.getButcheryProductionItems());
        newButcheryProductionDto.setButcheryProductionSourceViews(
                this.butcheryProductionSourceRepository
                        .findByButcheryProductionOrderByButcheryReceivingItemIdView(newButcheryProduction)
        );


        return newButcheryProductionDto;
    }

    @Override
    @Transactional
    public ButcheryProductionDto setButcheryProduction(ButcheryProductionDto butcheryProductionDto) {

        ButcheryProductionDto newButcheryProductionDto = new ButcheryProductionDto();

        Long butcheryProductionId = butcheryProductionDto.getButcheryProductionId();

        newButcheryProductionDto.setButcheryProductionId(butcheryProductionId);

        Optional<ButcheryProduction> optProd =
                this.butcheryProductionRepository.findByButcheryProductionId(butcheryProductionId);

        if (optProd.isPresent()) {

            ButcheryProduction butcheryProduction = optProd.get();

            newButcheryProductionDto.setProductionStatus(butcheryProduction.getProductionStatus());

            if (butcheryProduction.getProductionStatus().equals("Unposted")) {
                if (butcheryProductionDto.getWarehouse() != null) {
                    butcheryProduction.setWarehouse(butcheryProductionDto.getWarehouse());
                }
            }

            this.butcheryProductionRepository.save(butcheryProduction);

        }

        return newButcheryProductionDto;

    }

    @Override
    @Transactional
    public ButcheryProductionDto setProductionStatus(ButcheryProductionDto dto) {

        Long prodId = dto.getButcheryProductionId();

        ButcheryProductionDto  newButcheryProductionDto = new ButcheryProductionDto();

        newButcheryProductionDto.setButcheryProductionId(prodId);

        Optional<ButcheryProduction> optProd = this.butcheryProductionRepository.findByButcheryProductionId(prodId);

        if (optProd.isPresent()) {

            ButcheryProduction butcheryProduction = optProd.get();

            String oldStatus = butcheryProduction.getProductionStatus();
            String newStatus = dto.getProductionStatus();

            newButcheryProductionDto.setProductionStatus(oldStatus);

            if (oldStatus.equals("Unposted")) {

                butcheryProduction.setProductionStatus(newStatus);

                if (newStatus.equals("Posted")) {

                    Warehouse warehouse = butcheryProduction.getWarehouse();

                    Set<ButcheryProductionSource> butcheryProductionSources =
                            this.butcheryProductionSourceRepository
                                    .findByButcheryProductionOrderByButcheryReceivingItemId(butcheryProduction);

                    butcheryProductionSources.forEach(butcheryProductionSource -> {

                        Long receivingItemId = butcheryProductionSource
                                .getButcheryReceivingItem().getButcheryReceivingItemId();

                        Item item = butcheryProductionSource.getButcheryReceivingItem().getItem();

                        Optional<ItemCost> optItemCost = this.itemCostRepository
                                .findByItemAndWarehouse(item, warehouse);

                        if (optItemCost.isPresent()) {

                            ItemCost itemCost = optItemCost.get();

                            BigDecimal totalQty = butcheryProductionSource.getRequiredQty();

                            this.itemCostRepository.setQty(totalQty.multiply(new BigDecimal(-1)), itemCost.getItemCostId());

                            this.butcheryReceivingItemRepository.setUsedQty(totalQty, receivingItemId);

                        }

                    });

                    Set<ButcheryProductionItem> butcheryProductionItems =
                            this.butcheryProductionItemRepository
                                    .findByButcheryProductionOrderByItemName(butcheryProduction);

                    butcheryProductionItems.forEach(butcheryProductionItem -> {

                        butcheryProductionItem.setIsAvailable(true);
                        this.butcheryProductionItemRepository.save(butcheryProductionItem);

                        Item item = butcheryProductionItem.getItem();
                        BigDecimal baseQty = butcheryProductionItem.getBaseQty();
                        BigDecimal producedQty = butcheryProductionItem.getProducedQty();
                        BigDecimal productionCost = butcheryProductionItem.getProductionCost();
                        BigDecimal totalQty =  producedQty.multiply(baseQty);
                        BigDecimal cost = productionCost.divide(baseQty);

                        this.itemCostRepository.setQtyCost(totalQty, cost, item, warehouse);

                    });

                }

                this.butcheryProductionRepository.save(butcheryProduction);

            }

        }

        return newButcheryProductionDto;
    }

    @Override
    @Transactional
    public ButcheryProductionDto putButcheryProductionItem(
            ButcheryProductionItem butcheryProductionItem) {

        ButcheryProductionDto productionDto = new ButcheryProductionDto();

        Long productionId = butcheryProductionItem
                .getButcheryProduction().getButcheryProductionId();

        productionDto.setButcheryProductionId(productionId);

        Optional<ButcheryProduction> optProd = this.butcheryProductionRepository
                .findByButcheryProductionId(productionId);

        if (optProd.isPresent()) {

            ButcheryProduction butcheryProduction = optProd.get();

            productionDto.setProductionStatus(butcheryProduction.getProductionStatus());

            ButcheryProductionItem newProductionItem = new ButcheryProductionItem();

            newProductionItem.setButcheryProduction(butcheryProduction);

            if (butcheryProduction.getProductionStatus().equals("Unposted")) {

                if (butcheryProductionItem.getButcheryProductionItemId() != null) {

                    newProductionItem.setButcheryProductionItemId(
                            butcheryProductionItem.getButcheryProductionItemId());

                }

                Item item = null;

                Optional<Item> tmpItem = this.itemRepository.findById(
                        butcheryProductionItem.getItem().getItemId());

                if (tmpItem.isPresent()) {
                    item = tmpItem.get();
                }

                ItemUomId itemUomId = new ItemUomId();

                itemUomId.setItemId(item.getItemId());
                itemUomId.setUomId(butcheryProductionItem.getRequiredUom().getUomId());

                Optional<ItemUom> itemUom = this.itemUomRepository.findById(itemUomId);

                if (itemUom.isPresent()) {

                    newProductionItem.setBaseQty(itemUom.get().getQuantity());

                } else {

                    newProductionItem.setBaseQty(new BigDecimal(1));

                }

                newProductionItem.setItem(item);
                newProductionItem.setBarcode(butcheryProductionItem.getBarcode());
                newProductionItem.setBaseUom(item.getUom());
                newProductionItem.setItemClass(item.getItemClass());
                newProductionItem.setRequiredUom(butcheryProductionItem.getRequiredUom());
                newProductionItem.setProducedQty(butcheryProductionItem.getProducedQty());
                newProductionItem.setProductionCost(butcheryProductionItem.getProductionCost());
                newProductionItem.setTotalAmount((butcheryProductionItem.getTotalAmount()));
                newProductionItem.setWarehouse(butcheryProduction.getWarehouse());
                newProductionItem.setIsAvailable(false);

                this.butcheryProductionItemRepository.save(newProductionItem);

                butcheryProduction.setTotalAmount(this.butcheryProductionRepository.getTotalAmount(butcheryProduction));

                this.butcheryProductionRepository.save(butcheryProduction);

                productionDto.setButcheryProductionItem(newProductionItem);

            }

        }

        return productionDto;

    }

    @Override
    @Transactional
    public ButcheryProductionDto deleteButcheryProductionItem(
            ButcheryProductionItem butcheryProductionItem) {

        ButcheryProductionDto butcheryProductionDto =
                new ButcheryProductionDto();

        Long productionItemId = butcheryProductionItem.getButcheryProductionItemId();

        Long productionId = butcheryProductionItem.getButcheryProduction().getButcheryProductionId();

        butcheryProductionDto.setButcheryProductionId(productionId);

        Optional<ButcheryProduction> optProd = this.butcheryProductionRepository.findByButcheryProductionId(productionId);

        if (optProd.isPresent()) {
            ButcheryProduction production = optProd.get();

            butcheryProductionDto.setProductionStatus(production.getProductionStatus());

            if (production.getProductionStatus().equals("Unposted")) {

                this.butcheryProductionItemRepository.deleteById(productionItemId);

                production.setTotalAmount(this.butcheryProductionRepository
                        .getTotalAmount(production));
            }
        }

        return butcheryProductionDto;
    }

    @Override
    @Transactional
    public ButcheryProductionDto deleteButcheryProductionSource(ButcheryProductionSource butcheryProductionSource) {

        ButcheryProductionDto butcheryProductionDto =
                new ButcheryProductionDto();

        Long productionSourceId = butcheryProductionSource.getButcheryProductionSourceId();

        Long productionId = butcheryProductionSource.getButcheryProduction().getButcheryProductionId();

        butcheryProductionDto.setButcheryProductionId(productionId);

        Optional<ButcheryProduction> optProd = this.butcheryProductionRepository.findByButcheryProductionId(productionId);

        if (optProd.isPresent()) {
            ButcheryProduction production = optProd.get();

            butcheryProductionDto.setProductionStatus(production.getProductionStatus());

            if (production.getProductionStatus().equals("Unposted")) {

                this.butcheryProductionSourceRepository.deleteById(productionSourceId);

            } else {

            }
        }

        return butcheryProductionDto;
    }

    @Override
    @Transactional
    public ButcheryProductionDto putButcheryProductionSource(ButcheryProductionSource butcheryProductionSource) {
        ButcheryProductionDto productionDto = new ButcheryProductionDto();

        Long productionId = butcheryProductionSource
                .getButcheryProduction().getButcheryProductionId();

        productionDto.setButcheryProductionId(productionId);

        Optional<ButcheryProduction> optProd = this.butcheryProductionRepository
                .findByButcheryProductionId(productionId);

        if (optProd.isPresent()) {

            ButcheryProduction butcheryProduction = optProd.get();

            productionDto.setProductionStatus(butcheryProduction.getProductionStatus());

            ButcheryProductionSource newProductionSource = new ButcheryProductionSource();

            newProductionSource.setButcheryProduction(butcheryProduction);

            if (butcheryProduction.getProductionStatus().equals("Unposted")) {

                if (butcheryProductionSource.getButcheryProductionSourceId() != null) {

                    newProductionSource.setButcheryProductionSourceId(
                            butcheryProductionSource.getButcheryProductionSourceId());

                }

                Optional<ButcheryReceivingItem> optButRec = this.butcheryReceivingItemRepository
                        .findById(butcheryProductionSource.getButcheryReceivingItem().getButcheryReceivingItemId());

                if (optButRec.isPresent()) {
                    newProductionSource.setButcheryReceivingItem(optButRec.get());
                }
                newProductionSource.setRequiredQty(butcheryProductionSource.getRequiredQty());

                this.butcheryProductionSourceRepository.save(newProductionSource);

                productionDto.setButcheryProductionSourceView(
                        this.butcheryProductionSourceRepository
                                .findByIdView(newProductionSource
                                        .getButcheryProductionSourceId()).get()
                );

            }

        }

        return productionDto;
    }

    @Override
    public Set<ButcheryProductionSourceAggregatedView> unitTest(Long id) {

        Optional<ButcheryProduction> opt = this.butcheryProductionRepository.findById(id);

        return this.butcheryProductionSourceRepository.searchByButcheryProduction(opt.get());
    }

    @Override
    public Set<ButcheryProductionItemAggregatedView> unitTest2(Long id) {

        Optional<ButcheryProduction> opt = this.butcheryProductionRepository.findById(id);

        return this.butcheryProductionItemRepository.searchByButcheryProduction(opt.get());
    }

    @Override
    public Set<ButcheryProductionAggregatedView> unitTest3() {
        return this.butcheryProductionRepository.searchButcheryProduction();
    }

    @Override
    public ButcheryProductionDto findByButcheryReceivingId(Long butcheryReceivingId) {

        ButcheryProductionDto dto = new ButcheryProductionDto();
        dto.setButcheryProductionSourceShortViews(this.butcheryProductionSourceRepository.findByButcheryReceivingId(butcheryReceivingId));

        return dto;
    }
}
