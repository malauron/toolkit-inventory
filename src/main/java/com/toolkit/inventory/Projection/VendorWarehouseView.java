package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.VendorWarehouse;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "vendorWarehouseView", types = { VendorWarehouse.class })
public interface VendorWarehouseView {
    Long getVendorWarehouseId();
    String getVendorWarehouseName();
}
