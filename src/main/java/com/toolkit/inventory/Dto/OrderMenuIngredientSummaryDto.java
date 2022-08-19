package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderMenuIngredientSummaryDto {
  private Item item;
  private Uom baseUom;
  private BigDecimal baseQty;
  private BigDecimal requiredQty;
  private BigDecimal orderedQty;

  public OrderMenuIngredientSummaryDto(
          Item item,
          Uom baseUom,
          BigDecimal baseQty,
          BigDecimal requiredQty,
          BigDecimal orderedQty
  ){
    this.item = item;
    this.baseUom = baseUom;
    this.baseQty = baseQty;
    this.requiredQty = requiredQty;
    this.orderedQty = orderedQty;
  }
}
