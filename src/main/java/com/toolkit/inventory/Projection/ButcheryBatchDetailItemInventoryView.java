package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatchDetailItem;
import com.toolkit.inventory.Domain.Item;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryBatchDetailInventoryView", types = { ButcheryBatchDetailItem.class })
public interface ButcheryBatchDetailItemInventoryView {
    Item getItem();
    BigDecimal getReceivedQty();
    BigDecimal getReceivedWeightKg();
}
