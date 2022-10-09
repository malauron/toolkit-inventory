package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryProductionDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ButcheryProductionServiceImp implements ButcheryProductionService {

    private ButcheryProductionRepository butcheryProductionRepository;

    private ButcheryProductionItemRepository butcheryProductionItemRepository;

    private ItemRepository itemRepository;

    private ItemUomRepository itemUomRepository;

    private ItemCostRepository itemCostRepository;

    private ItemGenericRepository itemGenericRepository;

    private WarehouseRepository warehouseRepository;

    @Autowired
    public ButcheryProductionServiceImp(ButcheryProductionRepository butcheryProductionRepository,
                                        ButcheryProductionItemRepository butcheryProductionItemRepository,
                                        ItemRepository itemRepository, ItemUomRepository itemUomRepository,
                                        ItemCostRepository itemCostRepository,
                                        ItemGenericRepository itemGenericRepository,
                                        WarehouseRepository warehouseRepository) {
        this.butcheryProductionRepository = butcheryProductionRepository;
        this.butcheryProductionItemRepository = butcheryProductionItemRepository;
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.itemGenericRepository = itemGenericRepository;
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
            butcheryProductionDto.setTotalWeight(optProd.get().getTotalWeight());
            butcheryProductionDto.setProductionStatus(optProd.get().getProductionStatus());
            butcheryProductionDto.setDateCreated(optProd.get().getDateCreated());

            Set<ButcheryProductionItem> butcheryProductionItems =
                    this.butcheryProductionItemRepository
                            .findByButcheryProductionOrderByItemName(optProd.get());

            butcheryProductionDto.setButcheryProductionItems(butcheryProductionItems);
        }

        return butcheryProductionDto;

    }

    @Override
    @Transactional
    public ButcheryProduction save(ButcheryProductionDto butcheryProductionDto) {

        ButcheryProduction newButcheryProduction = new ButcheryProduction();

        newButcheryProduction.setTotalWeight(butcheryProductionDto.getTotalWeight());
        newButcheryProduction.setProductionStatus("Unposted");

        Optional<Warehouse> optWhse = this.warehouseRepository.findById(butcheryProductionDto.getWarehouse().getWarehouseId());

        if (optWhse.isPresent()) {
            newButcheryProduction.setWarehouse(optWhse.get());
        }

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
            newButcheryProductionItem.setItemClass(item.getItemClass());
            newButcheryProductionItem.setBaseUom(item.getUom());
            newButcheryProductionItem.setRequiredUom(butcheryProductionItem.getRequiredUom());
            newButcheryProductionItem.setProducedQty(butcheryProductionItem.getProducedQty());
            newButcheryProductionItem.setProductionCost(butcheryProductionItem.getProductionCost());
            newButcheryProductionItem.setTotalAmount(butcheryProductionItem.getTotalAmount());

            newButcheryProduction.addButcheryProductionItem(newButcheryProductionItem);

        });

        this.butcheryProductionRepository.save(newButcheryProduction);

        return newButcheryProduction;
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

                    Set<ButcheryProductionItem> butcheryProductionItems =
                            this.butcheryProductionItemRepository
                                    .findByButcheryProductionOrderByItemName(butcheryProduction);

                    butcheryProductionItems.forEach(butcheryProductionItem -> {

                        Item item = butcheryProductionItem.getItem();
                        BigDecimal baseQty = butcheryProductionItem.getBaseQty();
                        BigDecimal producedQty = butcheryProductionItem.getProducedQty();
                        BigDecimal productionCost = butcheryProductionItem.getProductionCost();
                        BigDecimal ttQty =  producedQty.multiply(baseQty);
                        BigDecimal cost = productionCost.divide(baseQty);

                        this.itemCostRepository.setQtyCost(ttQty, cost, item, warehouse);

                    });

                }

                this.butcheryProductionRepository.save(butcheryProduction);

            }

        }

        return newButcheryProductionDto;
    }
}
