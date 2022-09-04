package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import com.toolkit.inventory.Dto.CustomerDto;
import com.toolkit.inventory.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class CustomerServiceImp implements  CustomerService {

    @Autowired
    private CustomerRepository  customerRepository;

    @Override
    @Transactional
    public Long save(CustomerDto customerDto) throws IOException {
        Customer customer = customerDto.getCustomer();

        if (customerDto.getOrigCustomerPicture() != null) {
            CustomerPicture customerPicture = new CustomerPicture();
            customerPicture.setFile(customerDto.getOrigCustomerPicture().getBytes());
            customerPicture.setType(customerDto.getOrigCustomerPicture().getContentType());
            customerPicture.setCustomer(customer);
            customer.setCustomerPicture(customerPicture);
        }

        customerRepository.saveAndFlush(customer);

        return customer.getCustomerId();
    }
}
