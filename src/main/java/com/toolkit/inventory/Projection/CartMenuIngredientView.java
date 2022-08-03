package com.toolkit.inventory.Projection;


import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.math.BigInteger;

@Projection(name = "cartMenuIngredientView", types = {CartMenuIngredient.class})
public interface CartMenuIngredientView {

  Long getCartMenuIngredientId();
  Item getItem();
  Uom getBaseUom();
  BigDecimal getBaseQty();
  Uom getRequiredUom();
  BigDecimal getRequiredQty();
  BigDecimal getOrderedQty();
  BigInteger getMenuIngredientId();

}
