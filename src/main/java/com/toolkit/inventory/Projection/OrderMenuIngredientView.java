package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.OrderMenuIngredient;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "orderMenuIngredientView", types = {OrderMenuIngredient.class})
public interface OrderMenuIngredientView {
    Long getOrderMenuIngredientId();
    Item getItem();
    Uom getBaseUom();
    BigDecimal getBaseQty();
    Uom getRequiredUom();
    BigDecimal getRequiredQty();
    BigDecimal getOrderedQty();
    Long getMenuIngredientId();
}
