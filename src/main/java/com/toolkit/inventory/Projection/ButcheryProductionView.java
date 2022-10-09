package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "butcheryProductionView", types = {ButcheryProduction.class})
public interface ButcheryProductionView {
    Long getButcheryProductionId();
    Warehouse getWarehouse();
    BigDecimal getTotalWeight();
    String getProductionStatus();
    Date getDateCreated();
}
