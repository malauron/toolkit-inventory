package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "warehouses")
public class Warehouse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "warehouse_id")
  private Long warehouseId;

  @Column(name = "warehouse_name")
  private String warehouseName;
}
