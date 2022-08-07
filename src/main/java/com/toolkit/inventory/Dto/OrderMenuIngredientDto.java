package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderMenuIngredientDto {
    private Long orderMenuIngredientId;
    private Item item;
    private Uom requiredUom;
    private BigDecimal requiredQty;
    private BigDecimal orderedQty;
    private Long menuIngredientId;
}
