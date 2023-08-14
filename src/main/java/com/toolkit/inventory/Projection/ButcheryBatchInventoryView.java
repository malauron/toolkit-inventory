package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import com.toolkit.inventory.Domain.Item;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryBatchInventoryView", types = { ButcheryBatchInventory.class })
public interface ButcheryBatchInventoryView {
    Long getButcheryBatchInventoryId();
    ButcheryBatchView getButcheryBatch();
    Item getItem();
    BigDecimal getReceivedQty();
    BigDecimal getReceivedWeightKg();
    BigDecimal getRemainingQty();
    BigDecimal getRemainingWeightKg();
}
