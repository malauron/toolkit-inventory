package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Menu;
import com.toolkit.inventory.Domain.OrderMenu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Set;

@Projection(name = "orderMenuView", types = {OrderMenu.class})
public interface OrderMenuView {
    Long getOrderMenuId();
    Menu getMenu();
    BigDecimal getOrderQty();
    BigDecimal getPrice();
    BigDecimal getLineTotal();
    @Value("#{@orderMenuIngredientRepository.findOrderMenuIngredientsByOrderMenu(target)}")
    Set<OrderMenuIngredientView> getOrderMenuIngredients();
}
