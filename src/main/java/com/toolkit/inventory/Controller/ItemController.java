package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    public Item save(@RequestBody Item itemParam) {
        itemService.save(itemParam);
        return itemParam;
    }

    @PutMapping("/items")
    public Item update(@RequestBody Item itemParam) {
        itemService.save(itemParam);
        return itemParam;
    }

}
