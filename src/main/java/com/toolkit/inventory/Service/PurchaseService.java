package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Dto.PurchaseDto;

public interface PurchaseService {
  Purchase save(PurchaseDto purchaseDto);
  void setVendor(PurchaseDto purchaseDto);
}
