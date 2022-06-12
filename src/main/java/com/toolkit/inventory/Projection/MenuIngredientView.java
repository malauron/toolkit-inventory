package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.MenuIngredient;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "menuIngredientView", types = { MenuIngredient.class })
public interface MenuIngredientView {

    Long getMenuIngredientId();
    Item getItem();
    Uom getRequiredUom();
    BigDecimal getRequiredQty();

}
