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
public class VendorWarehouseInventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vendor_warehouse_inventory_id")
  private Long vendorWarehouseInventoryId;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "vendor_warehouse_id")
  private Warehouse vendorWarehouse;

  @Column(name = "qty")
  private BigDecimal qty;

  @Column(name = "weight_kg")
  private BigDecimal weightKg;

  @Version
  @Column(name = "version")
  private Long version;

}
