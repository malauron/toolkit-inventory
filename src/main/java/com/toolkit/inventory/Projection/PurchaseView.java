package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.Vendor;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "purchaseView", types = { Purchase.class })
public interface PurchaseView {
  Long getPurchaseId();
  Vendor getVendor();
  BigDecimal getTotalAmt();
  String getPurchaseStatus();
  Date getDateCreated();
  Date getDateUpdated();
}
