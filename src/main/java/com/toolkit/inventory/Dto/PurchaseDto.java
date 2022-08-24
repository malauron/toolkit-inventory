package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Domain.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class PurchaseDto {
  private BigDecimal totalAmt;
  private Vendor vendor;
  private Set<PurchaseItem> purchaseItems;

}
