package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.*;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryReceivingItemView", types = {ButcheryReceivingItem.class})
public interface ButcheryReceivingItemView {
  Long getButcheryReceivingItemId();
  ButcheryReceivingView getButcheryReceiving();
  Item getItem();
  Uom getBaseUom();
  BigDecimal getBaseQty();
  Uom getRequiredUom();
  BigDecimal getReceivedQty();
  BigDecimal getItemCost();
  BigDecimal getTotalAmount();
  BigDecimal getDocumentedQty();
  BigDecimal getUsedQty();
  String getRemarks();
  Boolean getIsAvailable();
}
