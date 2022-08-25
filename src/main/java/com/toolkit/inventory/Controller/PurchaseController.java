package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Domain.PurchaseItem;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Service.PurchaseItemService;
import com.toolkit.inventory.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PurchaseController {

  @Autowired
  private PurchaseService purchaseService;

  @Autowired
  private PurchaseItemService purchaseItemService;

  @PostMapping("/purchases")
  public Purchase save(@RequestBody PurchaseDto purchaseDto) {
    return this.purchaseService.save(purchaseDto);
  }

  @PutMapping("/purchases/vendor")
  public void setVendor(@RequestBody PurchaseDto purchaseDto) {
    this.purchaseService.setVendor(purchaseDto);
  }

  @PutMapping("/purchaseItems")
  public PurchaseItem putPurchaseItem(@RequestBody PurchaseItem purchaseItem) {
    return this.purchaseItemService.putPurchaseItem(purchaseItem);
  }
}
