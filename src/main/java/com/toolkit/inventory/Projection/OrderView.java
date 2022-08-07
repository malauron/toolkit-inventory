package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.Order;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "orderView", types = {Order.class})
public interface OrderView {
    Long getOrderId();
    Customer getCustomer();
    BigDecimal getTotalPrice();

}
