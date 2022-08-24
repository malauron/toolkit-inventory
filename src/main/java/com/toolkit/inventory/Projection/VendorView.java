package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Vendor;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "vendorView", types = { Vendor.class })
public interface VendorView {
  Long getVendorId();
  String getVendorName();
  String getContactNo();
  String getAddress();
  Date getDateCreated();
  Date getDateUpdated();
}