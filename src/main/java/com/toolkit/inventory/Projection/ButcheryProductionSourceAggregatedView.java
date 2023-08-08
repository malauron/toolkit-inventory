package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProductionSource;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryProductionSourceAggregatedView", types = { ButcheryProductionSource.class })
public interface ButcheryProductionSourceAggregatedView {
    BigDecimal getRequiredQty();
}
