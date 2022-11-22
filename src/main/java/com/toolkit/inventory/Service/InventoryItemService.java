package com.toolkit.inventory.Service;

import java.math.BigDecimal;

public interface InventoryItemService {

    void setEndingQty(BigDecimal qty, Long inventoryItemId);
}
