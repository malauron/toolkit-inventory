package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "inventoryItemView", types = { InventoryItem.class })
public interface InventoryItemView {

    Long getInventoryItemId();
    ItemView getItem();
    Warehouse getWarehouse();
    BigDecimal getBeginningQty();
    BigDecimal getPurchasedQty();
    BigDecimal getEndingQty();
    BigDecimal getCost();
    BigDecimal getPrice();

}
