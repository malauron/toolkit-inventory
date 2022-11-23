package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Dto.InventoryItemDto;
import com.toolkit.inventory.Service.InventoryItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/inventoryItems")
    public Set<InventoryItem> findAllByWarehouseIdWithQty(Long warehouseId) {

        return this.inventoryItemService.findAllByWarehouseIdWithQty(warehouseId);

    }

    @PutMapping("/inventoryItems/price")
    public void setPrice(@RequestBody InventoryItemDto inventoryItemDto) {
        this.inventoryItemService.setPrice(inventoryItemDto);
    }

    @PutMapping("/inventoryItems/endingQty")
    public void setEndingQty(@RequestBody InventoryItemDto inventoryItemDto) {
        this.inventoryItemService.setEndingQty(
                inventoryItemDto.getQty(),
                inventoryItemDto.getInventoryItemId());
    }
}
