package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import com.toolkit.inventory.Domain.CustomerSignature;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customerSingleView", types = { Customer.class })
public interface CustomerSingleView {
  Long getCustomerId();
  String getCustomerName();
  String getContactNo();
  String getAddress();
  CustomerPicture getCustomerPicture();
  CustomerSignature getCustomerSignature();
  String getSssNo();
  String getHdmfNo();
  String getPhicNo();
  String getTinNo();
  String getBloodType();
  String getErContactPerson();
  String getErContactNo();
  String getErContactAddress();
}
