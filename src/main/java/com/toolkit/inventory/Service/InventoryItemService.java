package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Dto.InventoryItemDto;

import java.math.BigDecimal;
import java.util.Set;

public interface InventoryItemService {

    Set<InventoryItem> findAllByWarehouseIdWithQty(Long warehouseId);
    void setPrice(InventoryItemDto inventoryItemDto);
    void setEndingQty(BigDecimal qty, Long inventoryItemId);
    void setQty(Long id);
}
