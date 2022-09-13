package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import com.toolkit.inventory.Domain.CustomerSignature;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "customerPageView", types = { Customer.class })
public interface CustomerPageView {
  Long getCustomerId();
  String getCustomerCode();
  String getCustomerName();
  CustomerPicture getCustomerPicture();
  CustomerSignature getCustomerSignature();
}
