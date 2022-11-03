package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Projection(name = "butcheryProductionAggregatedView", types = {ButcheryProduction.class})
public interface ButcheryProductionAggregatedView {
    Long getButcheryProductionId();
    Warehouse getWarehouse();
    BigDecimal getTotalAmount();
    String getProductionStatus();
    Date getDateCreated();

    @Value("#{@butcheryProductionItemRepository.searchByButcheryProduction(target)}")
    Set<ButcheryProductionItemAggregatedView> getButcheryProductionItems();

    @Value("#{@butcheryProductionSourceRepository.searchByButcheryProduction(target)}")
    Set<ButcheryProductionSourceAggregatedView> getButcheryProductionSources();
}
