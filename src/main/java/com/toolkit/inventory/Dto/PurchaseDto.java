package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Domain.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class PurchaseDto {
  private Long purchaseId;
  private Vendor vendor;
  private BigDecimal totalAmt;
  private String purchaseStatus;
  private Set<PurchaseItem> purchaseItems;
  private PurchaseItem purchaseItem;
  private Date dateCreated;
}
