package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Dto.PurchaseDto;

public interface PurchaseItemService {
  PurchaseDto putPurchaseItem(PurchaseItem purchaseItem);
  PurchaseDto delete(PurchaseItem purchaseItem);
}
