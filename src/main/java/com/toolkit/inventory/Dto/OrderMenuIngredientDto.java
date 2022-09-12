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
    private Uom baseUom;
    private Uom requiredUom;
    private BigDecimal baseQty;
    private BigDecimal requiredQty;
    private BigDecimal orderedQty;
    private BigDecimal cost;
    private Long menuIngredientId;
}
