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

  @GetMapping("/purchases")
  public PurchaseDto getPurchase(@RequestParam Long purchaseId){

    return this.purchaseService.getPurchase(purchaseId);

  }

  @PostMapping("/purchases")
  public Purchase save(@RequestBody PurchaseDto purchaseDto) {

    return this.purchaseService.save(purchaseDto);

  }

  @PutMapping("/purchases")
  public PurchaseDto setPurchase(@RequestBody PurchaseDto purchaseDto) {

    return this.purchaseService.setPurchase(purchaseDto);

  }

  @PutMapping("/purchases/purchaseStatus")
  public PurchaseDto setPurchaseStatus(@RequestBody PurchaseDto purchaseDto) {

    return this.purchaseService.setPurchaseStatus(purchaseDto);

  }

  @PutMapping("/purchaseItems")
  public PurchaseDto putPurchaseItem(@RequestBody PurchaseItem item) {

    return this.purchaseItemService.putPurchaseItem(item);

  }

  @DeleteMapping("/purchaseItems")
  public PurchaseDto deletePurchaseItemById(@RequestBody PurchaseItem purchaseItem) {

    return this.purchaseItemService.delete(purchaseItem);

  }

}
