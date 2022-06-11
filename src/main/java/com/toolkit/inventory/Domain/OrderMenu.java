package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderMenuId")
@Table(name = "order_menus")
public class OrderMenu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_menu_id")
  private Long orderMenuId;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @Column(name = "menu_id")
  private Long menuId;

  @Column(name = "order_qty")
  private BigDecimal orderQty;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "line_total")
  private BigDecimal lineTotal;

  @OneToMany(mappedBy = "orderMenu", cascade = CascadeType.ALL)
  Set<OrderMenuIngredient> orderMenuIngredients = new HashSet<>();

}
