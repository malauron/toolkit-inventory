package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Menu;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderMenuDto {
    private Long orderMenuId;
    private Menu menu;
    private BigDecimal orderQty;
    private BigDecimal price;
    private BigDecimal lineTotal;
    private List<OrderMenuIngredientDto> orderMenuIngredients;

    public void addIngredient(OrderMenuIngredientDto ingredient) {
        if (ingredient != null) {
            if (orderMenuIngredients == null) {
                orderMenuIngredients = new ArrayList<>();
            }

            orderMenuIngredients.add(ingredient);
        }
    }
}
