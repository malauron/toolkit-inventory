package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.*;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "itemCostView", types = {ItemCost.class})
public interface ItemCostView {

  Long getItemCostId();
  ItemView getItem();
  Warehouse getWarehouse();
  BigDecimal getQty();
  BigDecimal getWeightKg();
  BigDecimal getCost();

}
