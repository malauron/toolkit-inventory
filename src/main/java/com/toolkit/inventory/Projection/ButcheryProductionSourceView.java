package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Projection(name = "butcheryProductionSourceView", types = { ButcheryProductionSource.class })
public interface ButcheryProductionSourceView {
    Long getButcheryProductionSourceId();
    Item getItem();
    Uom getRequiredUom();
    BigDecimal getRequiredQty();
    BigDecimal getRequiredWeightKg();
}
