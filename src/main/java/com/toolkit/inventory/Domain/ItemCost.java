package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

  @Column(name = "cost")
  private BigDecimal cost;

  @Version
  private long  version;

}
