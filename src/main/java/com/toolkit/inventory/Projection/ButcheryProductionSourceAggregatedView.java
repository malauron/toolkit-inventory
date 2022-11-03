package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryProductionSourceAggregatedView", types = { ButcheryProductionSource.class })
public interface ButcheryProductionSourceAggregatedView {
    ButcheryReceivingItem getButcheryReceivingItem();
    BigDecimal getRequiredQty();
}
