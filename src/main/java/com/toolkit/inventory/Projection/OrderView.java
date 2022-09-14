package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.Order;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "orderView", types = { Order.class })
public interface OrderView {
    Long getOrderId();
    Customer getCustomer();
    BigDecimal getTotalPrice();
    Warehouse getWarehouse();
    String getOrderStatus();
    Date getDateCreated();
    Date getDateUpdated();
}
