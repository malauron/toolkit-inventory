package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.InventoryHistoryItem;
import com.toolkit.inventory.Repository.InventoryHistoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InventoryHistoryServiceImp implements InventoryHistoryService {

    private InventoryHistoryItemRepository inventoryHistoryItemRepository;

    public InventoryHistoryServiceImp(
            InventoryHistoryItemRepository inventoryHistoryItemRepository
    ) {
        this.inventoryHistoryItemRepository = inventoryHistoryItemRepository;
    }

    @Override
    public Set<InventoryHistoryItem> findAllByInventoryHistoryId(Long inventoryHistoryId) {
        return this.inventoryHistoryItemRepository
                .findByInventoryHistoryId(inventoryHistoryId);
    }
}
