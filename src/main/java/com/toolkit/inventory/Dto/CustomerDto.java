package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDto {
    private Customer customer;
    private CustomerPicture customerPicture;
}
