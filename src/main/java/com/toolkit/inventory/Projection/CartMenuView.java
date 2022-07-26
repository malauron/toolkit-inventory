package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.CartMenu;
import com.toolkit.inventory.Domain.CartMenuIngredient;
import com.toolkit.inventory.Domain.Menu;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Projection(name = "cartMenuView", types = {CartMenu.class})
public interface CartMenuView {

  Long getCartMenuId();
  Menu getMenu();
  BigDecimal getOrderQty();
  BigDecimal getPrice();
  BigDecimal getLineTotal();
  Set<CartMenuIngredientView> getCartMenuIngredients();

}
