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

    private WarehouseRepository warehouseRepository;

    public ItemServiceImp(ItemRepository itemRepository,
                          ItemUomRepository itemUomRepository,
                          ItemCostRepository itemCostRepository,
                          UomRepository uomRepository,
                          WarehouseRepository warehouseRepository) {
        this.itemRepository = itemRepository;
        this.itemUomRepository = itemUomRepository;
        this.itemCostRepository = itemCostRepository;
        this.uomRepository = uomRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    @Override
    public ItemDto save(ItemDto itemDto) {

        ItemDto newItemDto = new ItemDto();

        if (itemDto.getItem().getItemId() > 0) {

            Optional<Item> optItem    = this.itemRepository.findById(itemDto.getItem().getItemId());

            if (optItem.isPresent()) {
                Item item = optItem.get();

                item.setItemName(itemDto.getItem().getItemName());
                item.setIsActive(itemDto.getItem().getIsActive());

                this.itemRepository.save(item);
            }

        } else {

            Item newItem = this.itemRepository.saveAndFlush(itemDto.getItem());

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

            });

            System.out.println(newItem.getItemId());

            newItemDto.setItem(newItem);
            newItemDto.setItemUoms(itemUoms);

            List<Warehouse> warehouses = this.warehouseRepository.findAll();

            warehouses.forEach(warehouse -> {

                ItemCost itemCost = new ItemCost();

                itemCost.setItem(newItem);
                itemCost.setWarehouse(warehouse);
                itemCost.setQty(BigDecimal.ZERO);
                itemCost.setCost(BigDecimal.ZERO);

                this.itemCostRepository.save(itemCost);

            });

        }

        return newItemDto;

    }

}
