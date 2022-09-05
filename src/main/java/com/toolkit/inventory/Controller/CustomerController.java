package com.toolkit.inventory.Controller;

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
    public Long save(
            @RequestPart CustomerDto customerDto,
            @RequestPart(required = false) MultipartFile pictureFile,
            @RequestPart(required = false) MultipartFile signatureFile) throws IOException, Exception {

        return this.customerService.save(customerDto, pictureFile, signatureFile);

    }
}
