package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.InventoryHistoryItem;
import com.toolkit.inventory.Dto.InventoryHistoryDto;
import com.toolkit.inventory.Service.InventoryHistoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class InventoryHistoryController {

    private InventoryHistoryService inventoryHistoryService;

    public InventoryHistoryController(
            InventoryHistoryService inventoryHistoryService
    ){
        this.inventoryHistoryService = inventoryHistoryService;
    }

    @GetMapping("/inventoryHistoryItems")
    public Set<InventoryHistoryItem> findByInventoryHistoryId(InventoryHistoryDto inventoryHistoryDto) {
        return this.inventoryHistoryService
                .findAllByInventoryHistoryId(inventoryHistoryDto.getInventoryHistoryId());
    }

}
