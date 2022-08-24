package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Purchase;
import com.toolkit.inventory.Dto.PurchaseDto;
import com.toolkit.inventory.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PurchaseController {

  @Autowired
  private PurchaseService purchaseService;

  @PostMapping("/purchases")
  public Purchase save(@RequestBody PurchaseDto purchaseDto) {
    System.out.println(purchaseDto.getTotalAmt());
    return this.purchaseService.save(purchaseDto);
  }
}
