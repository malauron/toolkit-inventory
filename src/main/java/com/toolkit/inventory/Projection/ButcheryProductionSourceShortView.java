package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryProductionSourceShortView", types = { ButcheryProductionSource.class })
public interface ButcheryProductionSourceShortView {
    ButcheryProductionAggregatedView getButcheryProduction();
}
