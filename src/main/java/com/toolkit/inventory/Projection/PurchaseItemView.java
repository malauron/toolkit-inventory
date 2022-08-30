package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "purchaseItemView", types = { PurchaseItem.class })
public interface PurchaseItemView {
    Long getPurchaseItemId();
    Item getItem();
    Uom getBaseUom();
    BigDecimal getBaseQty();
    Uom getRequiredUom();
    BigDecimal getPurchasedQty();
    BigDecimal getCost();
}
