package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "warehouseView", types = { Warehouse.class })
public interface WarehouseView {
  Long getWarehouseId();
  String getWarehouseName();
}
