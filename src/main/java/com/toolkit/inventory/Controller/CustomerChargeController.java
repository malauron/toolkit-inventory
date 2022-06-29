package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.CustomerCharge;
import com.toolkit.inventory.Service.CustomerChargeService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CustomerChargeController {

  private CustomerChargeService customerChargeService;

  public CustomerChargeController(CustomerChargeService customerChargeService) {
    this.customerChargeService = customerChargeService;
  }

  @PostMapping("/customerCharges")
  public String save(@RequestBody CustomerCharge customerChargeParam) {
    customerChargeService.save(customerChargeParam);
    return "Ok";
  }

}
