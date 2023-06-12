package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
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

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Column(name = "order_qty")
  private BigDecimal orderQty;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "line_total")
  private BigDecimal lineTotal;

  @JsonManagedReference
  @OneToMany(mappedBy = "orderMenu", cascade = CascadeType.ALL)
  Set<OrderMenuIngredient> orderMenuIngredients = new HashSet<>();

  public void addIngredient(OrderMenuIngredient ingredient) {

    if (ingredient != null) {
      if (orderMenuIngredients == null) {
        orderMenuIngredients = new HashSet<>();
      }

      orderMenuIngredients.add(ingredient);
      ingredient.setOrderMenu(this);
    }
  }
}
