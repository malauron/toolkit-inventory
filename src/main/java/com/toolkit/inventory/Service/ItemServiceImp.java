package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemCost;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Repository.ItemCostRepository;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ItemCostRepository itemCostRepository;

    public ItemServiceImp(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public void save(Item item) {

        List<Warehouse> warehouses = this.warehouseRepository.findAll();

        warehouses.forEach(warehouse -> {

            ItemCost itemCost = new ItemCost();

            itemCost.setWarehouse(warehouse);
            itemCost.setQty(new BigDecimal(0L));
            itemCost.setCost(new BigDecimal(0));

            item.addItemCost(itemCost);

        });

        itemRepository.save(item);

    }

}
