package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Dto.CustomerDto;
import com.toolkit.inventory.Service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PutMapping("/customers")
    public Long save(@RequestBody CustomerDto customerDto) {
        return this.customerService.save(customerDto);
    }

    @PutMapping("/customers/pictures")
    public Long save(@RequestPart MultipartFile pictureFile, @RequestPart CustomerDto customerDto) {

        try {

            return this.customerService.save(pictureFile, customerDto);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
