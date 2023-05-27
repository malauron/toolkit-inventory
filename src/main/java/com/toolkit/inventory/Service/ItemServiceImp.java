package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ItemDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemServiceImp implements ItemService {

    private ItemRepository itemRepository;

    private ItemUomRepository itemUomRepository;

    private ItemCostRepository itemCostRepository;

    private UomRepository uomRepository;

    private ItemBomRepository itemBomRepository;

    private ItemGenericRepository itemGenericRepository;

    private InventoryItemRepository inventoryItemRepository;

    private WarehouseRepository warehouseRepository;

    public ItemServiceImp(ItemRepository itemRepository,
                          ItemUomRepository itemUomRepository,
                          ItemCostRepository itemCostRepository,
                          UomRepository uomRepository,
                          ItemBomRepository itemBomRepository,
                          ItemGenericRepository itemGenericRepository,
                          InventoryItemRepository inventoryItemRepository,
                          WarehouseRepository warehouseRepository) {
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.uomRepository = uomRepository;
        this.itemBomRepository = itemBomRepository;
        this.itemGenericRepository = itemGenericRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ItemDto findByItemCode(String itemCode) {

        ItemDto itemDto = new ItemDto();

        Optional<Item> optItem = this.itemRepository.findByItemCode(itemCode);

        optItem.ifPresent(itemDto::setItem);

        return itemDto;

    }

    @Transactional
    @Override
    public ItemDto save(ItemDto itemDto) {

        ItemDto newItemDto = new ItemDto();

        if (itemDto.getItem().getItemId() > 0) {

            Optional<Item> optItem = this.itemRepository
                                            .findById(itemDto.getItem().getItemId());

            if (optItem.isPresent()) {
                Item item = optItem.get();

                item.setItemCode(itemDto.getItem().getItemCode());
                item.setItemName(itemDto.getItem().getItemName());
                item.setIsActive(itemDto.getItem().getIsActive());
                item.setPrice(itemDto.getItem().getPrice());

                this.itemRepository.save(item);
            }

        } else {

            Item newItem = this.itemRepository.saveAndFlush(itemDto.getItem());

            newItemDto.setItem(newItem);

            if (newItem.getItemClass() == ItemClass.Stock) {

                //Alternative Unit of Measure
                Set<ItemUom> itemUoms = new HashSet<>();

                itemDto.getItemUoms().forEach(itemUom -> {

                    Optional<Uom> optUom = this.uomRepository.findById(itemUom.getUom().getUomId());

                    ItemUom newItemUom = new ItemUom();

                    newItemUom.setItemId(newItem.getItemId());
                    newItemUom.setItem(newItem);
                    newItemUom.setUom(optUom.get());
                    newItemUom.setUomId(optUom.get().getUomId());
                    newItemUom.setQuantity(itemUom.getQuantity());

                    itemUoms.add(this.itemUomRepository.save(newItemUom));

                    newItemDto.setItemUoms(itemUoms);

                });

            } else if (newItem.getItemClass() == ItemClass.Assembly) {

                //Bill of Materials
                Set<ItemBom> itemBoms = new HashSet<>();

                itemDto.getItemBoms().forEach(itemBom -> {

                    Optional<Item> optSubItem = this.itemRepository.findById(itemBom.getSubItem().getItemId());

                    Optional<Uom> optReqUom = this.uomRepository.findById(itemBom.getRequiredUom().getUomId());

                    ItemBom newItemBom = new ItemBom();

                    newItemBom.setMainItem(newItem);
                    newItemBom.setSubItem(optSubItem.get());
                    newItemBom.setRequiredUom(optReqUom.get());
                    newItemBom.setRequiredQty(itemBom.getRequiredQty());

                    itemBoms.add(this.itemBomRepository.save(newItemBom));

                    newItemDto.setItemBoms(itemBoms);

                });

            } else if (newItem.getItemClass() == ItemClass.Branded) {

                //Generic Item
                ItemGeneric itemGeneric = new ItemGeneric();

                itemGeneric.setMainItem(newItem);
                itemGeneric.setSubItem(itemDto.getItemGeneric().getSubItem());
                itemGeneric.setRequiredUom(itemDto.getItemGeneric().getRequiredUom());
                itemGeneric.setRequiredQty(itemDto.getItemGeneric().getRequiredQty());

                itemGeneric = this.itemGenericRepository.save(itemGeneric);

                newItemDto.setItemGeneric(itemGeneric);

            }

            List<Warehouse> warehouses = this.warehouseRepository.findAll();

            warehouses.forEach(warehouse -> {

                ItemCost itemCost = new ItemCost();

                itemCost.setItem(newItem);
                itemCost.setWarehouse(warehouse);
                itemCost.setQty(BigDecimal.ZERO);
                itemCost.setCost(BigDecimal.ZERO);

                this.itemCostRepository.save(itemCost);

                InventoryItem inventoryItem = new InventoryItem();

                inventoryItem.setItem(newItem);
                inventoryItem.setWarehouse(warehouse);
                inventoryItem.setBeginningQty(BigDecimal.ZERO);
                inventoryItem.setPurchasedQty(BigDecimal.ZERO);
                inventoryItem.setEndingQty(BigDecimal.ZERO);
                inventoryItem.setCost(BigDecimal.ZERO);
                inventoryItem.setPrice(newItem.getPrice());

                this.inventoryItemRepository.save(inventoryItem);

            });

        }

        return newItemDto;

    }

    @Override
    public ItemDto getItemBom(Long itemId) {
        Optional<Item> optItem = this.itemRepository.findById(itemId);

        Set<ItemBom> itemBoms = this.itemBomRepository.findByMainItemOrderBySubItemName(optItem.get());

        ItemDto itemDto = new ItemDto();

        itemDto.setItemBoms(itemBoms);

        return itemDto;
    }

    @Transactional
    @Override
    public ItemBom addItemBom(ItemBom itemBom) {

        return this.itemBomRepository.save(itemBom);

    }

    @Transactional
    @Override
    public void deleteItemBom(Long itemBomId){

        this.itemBomRepository.deleteById(itemBomId);

    }

    @Override
    public ItemDto getItemGeneric(Long itemId) {
        Optional<Item> optItem = this.itemRepository.findById(itemId);

        ItemGeneric itemGeneric = this.itemGenericRepository.findByMainItemOrderBySubItemName(optItem.get());

        ItemDto itemDto = new ItemDto();

        itemDto.setItemGeneric(itemGeneric);

        return itemDto;
    }

    @Override
    public ItemGeneric updateItemGeneric(ItemGeneric tmpItmGen) {

        Optional<ItemGeneric> optItmGen = this.itemGenericRepository.findById(tmpItmGen.getItemGenericId());

        if (optItmGen.isPresent()) {

            ItemGeneric itemGeneric = optItmGen.get();

            itemGeneric.setSubItem(tmpItmGen.getSubItem());
            itemGeneric.setRequiredUom(tmpItmGen.getRequiredUom());
            itemGeneric.setRequiredQty(tmpItmGen.getRequiredQty());

            return this.itemGenericRepository.save(itemGeneric);

        }

        return null;
    }

    @Override
    public Set<ItemCost> getItemCosts(Long warehouseId) {
        return this.itemCostRepository.findByWarehouseId(warehouseId);
    }


}