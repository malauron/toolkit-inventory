package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.CustomerDto;
import com.toolkit.inventory.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public Long save(@RequestBody CustomerDto customerDto) {
        return this.customerService.save(customerDto);
    }
}
