package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Projection(name = "butcheryProductionSourceView", types = { ButcheryProductionSource.class })
public interface ButcheryProductionSourceView {
    Long getButcheryProductionSourceId();
    BigDecimal getRequiredQty();
}
