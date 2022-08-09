package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Customer;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "customerView", types = { Customer.class })
public interface CustomerView {
  Long getCustomerId();
  String getCustomerName();
  String getContactNo();
  String getAddress();
  Date getDateCreated();
  Date getDateUpdated();
}
