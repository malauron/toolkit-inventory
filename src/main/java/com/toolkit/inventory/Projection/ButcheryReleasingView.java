package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "butcheryReleasingView", types = {ButcheryReleasing.class})
public interface ButcheryReleasingView {
    Long getButcheryReleasingId();
    Warehouse getWarehouse();
    Warehouse getDestinationWarehouse();
    Customer getCustomer();
    BigDecimal getTotalAmount();
    BigDecimal getTotalWeightKg();
    String getReleasingStatus();
    Date getDateCreated();
}
