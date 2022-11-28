package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.InventoryHistoryItem;

import java.util.Set;

public interface InventoryHistoryService {

    Set<InventoryHistoryItem> findAllByInventoryHistoryId(Long inventoryHistoryId);

}
