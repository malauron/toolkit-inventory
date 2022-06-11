package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemServiceImp(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

}
