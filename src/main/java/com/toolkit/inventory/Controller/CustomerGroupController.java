package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.CustomerGroup;
import com.toolkit.inventory.Service.CustomerGroupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CustomerGroupController {

  private CustomerGroupService customerGroupService;

  public CustomerGroupController(CustomerGroupService customerGroupService) {

    this.customerGroupService = customerGroupService;

  }

  @GetMapping("/customerGroups")
  public List<CustomerGroup> findAll() {

    return this.customerGroupService.findAll();

  }

}
