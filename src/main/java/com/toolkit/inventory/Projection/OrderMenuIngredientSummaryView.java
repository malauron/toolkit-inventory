package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

public interface OrderMenuIngredientSummaryView {
  ItemShortDescriptionView getItem();
  Uom getBaseUom();
  BigDecimal getTotalQty();
}
