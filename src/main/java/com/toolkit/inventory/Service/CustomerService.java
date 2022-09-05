package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.CustomerDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {

    Long save(CustomerDto customerDto,
              MultipartFile pictureFile,
              MultipartFile signatureFile) throws IOException, Exception;

}
