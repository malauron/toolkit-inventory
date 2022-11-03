package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryProductionItemAggregatedView", types = {ButcheryReceivingItem.class})
public interface ButcheryProductionItemAggregatedView {
  Item getItem();
  Uom getRequiredUom();
  BigDecimal getProducedQty();
  BigDecimal getTotalAmount();
}
