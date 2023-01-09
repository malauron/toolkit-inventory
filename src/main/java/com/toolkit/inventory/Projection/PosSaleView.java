package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.PosSale;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "posSaleView", types = {PosSale.class})
public interface PosSaleView {
    Long getPosSaleId();
    Warehouse getWarehouse();
    Customer getCustomer();
    BigDecimal getTotalAmount();
    String getSaleStatus();
    Date getDateCreated();
}
