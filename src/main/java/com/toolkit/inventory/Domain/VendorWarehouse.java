package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "vendor_warehouses")
public class VendorWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_warehouse_id")
    private Long vendorWarehouseId;

    @Column(name = "vendor_warehouse_name")
    private String vendorWarehouseName;
}
