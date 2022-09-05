package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.CustomerDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {

    Long save(MultipartFile pictureFile, CustomerDto customerDto) throws IOException, Exception;
    Long save(CustomerDto customerDto);

}
