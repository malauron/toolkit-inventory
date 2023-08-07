package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "item_costs")
public class ItemCost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_cost_id")
  private Long itemCostId;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  @Column(name = "qty")
  private BigDecimal qty;

  @Column(name = "weight_kg")
  private BigDecimal weightKg;

  @Column(name = "cost")
  private BigDecimal cost;

  @Version
  @Column(name = "version")
  private Long version;

}
