package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Dto.PurchaseDto;

public interface PurchaseService {
  PurchaseDto getPurchase(Long purchaseId);
  Purchase save(PurchaseDto purchaseDto);
  PurchaseDto setVendor(PurchaseDto purchaseDto);
  PurchaseDto setPurchaseStatus(PurchaseDto purchaseDto);
}
