package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderMenuIngredientId")
@Table(name = "order_menu_ingredients")
public class OrderMenuIngredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_menu_ingredient_id")
  private Long orderMenuIngredientId;

  @ManyToOne
  @JoinColumn(name = "order_menu_id")
  private OrderMenu orderMenu;

  @Column(name = "base_uom_id")
  private Long baseUomId;

  @Column(name = "base_qty")
  private BigDecimal baseQty;

  @Column(name = "required_uom_id")
  private Long requiredUomId;

  @Column(name = "required_qty")
  private BigDecimal requiredQty;

  @Column(name = "ordered_qty")
  private BigDecimal orderedQty;
}
