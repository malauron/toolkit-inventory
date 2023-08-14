package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import com.toolkit.inventory.Domain.Item;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryBatchInventorySummary", types = {ButcheryBatchInventory.class })
public interface ButcheryBatchInventorySummary {
    Item getItem();
    BigDecimal getReceivedQty();
    BigDecimal getReceivedWeightKg();
    BigDecimal getRemainingQty();
    BigDecimal getRemainingWeightKg();
}
