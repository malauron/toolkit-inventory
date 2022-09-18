package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.ItemDto;
import com.toolkit.inventory.Service.ItemService;
import com.toolkit.inventory.Service.ItemUomService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private ItemService itemService;

    private ItemUomService itemUomService;

    public ItemController(ItemService itemService,
                          ItemUomService itemUomService) {
        this.itemService = itemService;
        this.itemUomService = itemUomService;
    }

    @PostMapping("/items")
    public ItemDto save(@RequestBody ItemDto itemDto) {

        return itemService.save(itemDto);

    }

    @PutMapping("/items")
    public ItemDto update(@RequestBody ItemDto itemDto) {
        itemService.save(itemDto);
        return itemDto;
    }

    @GetMapping("/itemUoms")
    public ItemDto getItemUoms(@RequestParam Long itemId) {

        return itemUomService.getItemUoms(itemId);

    }

}
