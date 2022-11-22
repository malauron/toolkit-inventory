package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.InventoryItemDto;
import com.toolkit.inventory.Service.InventoryItemService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class InventoryItemController {

    private InventoryItemService inventoryItemService;

    public InventoryItemController(
            InventoryItemService inventoryItemService
    ){
        this.inventoryItemService = inventoryItemService;
    }

    @PutMapping("/inventoryItems/endingQty")
    public void setEndingQty(@RequestBody InventoryItemDto inventoryItemDto) {
        this.inventoryItemService.setEndingQty(
                inventoryItemDto.getQty(),
                inventoryItemDto.getInventoryItemId());
    }
}
