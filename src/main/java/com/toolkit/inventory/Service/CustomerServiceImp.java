package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import com.toolkit.inventory.Domain.CustomerSignature;
import com.toolkit.inventory.Dto.CustomerDto;
import com.toolkit.inventory.Repository.CustomerPictureRepository;
import com.toolkit.inventory.Repository.CustomerRepository;
import com.toolkit.inventory.Repository.CustomerSignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class CustomerServiceImp implements  CustomerService {

    @Autowired
    private CustomerRepository  customerRepository;

    @Autowired
    private CustomerPictureRepository customerPictureRepository;

    @Autowired
    private CustomerSignatureRepository customerSignatureRepository;

    @Override
    @Transactional
    public Long save(CustomerDto customerDto, MultipartFile pictureFile, MultipartFile signatureFile) throws IOException, Exception {

        Customer customer = null;

        if (customerDto.getCustomer().getCustomerId() == null) {
            customer = new Customer();
        } else {
            Optional<Customer> tmp = this.customerRepository.findById(customerDto.getCustomer().getCustomerId());
            if (tmp.isPresent()) {
                customer = tmp.get();
            }
        }

        customerRepository.save(getCustomer(customer, customerDto));

        if (pictureFile != null) {
            CustomerPicture customerPicture;
            if (customerDto.getCustomer().getCustomerId() != null) {
                customerPicture = this.customerPictureRepository.findByCustomer(customerDto.getCustomer());

                if (customerPicture == null) {
                    customerPicture = new CustomerPicture();
                }
            } else {
                customerPicture = new CustomerPicture();
            }
            customerPicture.setFile(pictureFile.getBytes());
            customerPicture.setType(pictureFile.getContentType());
            customerPicture.setCustomer(customer);
            this.customerPictureRepository.save(customerPicture);
        }

        if (signatureFile != null) {
            CustomerSignature customerSignature;
            if (customerDto.getCustomer().getCustomerId() != null) {
                customerSignature = this.customerSignatureRepository.findByCustomer(customerDto.getCustomer());

                if (customerSignature == null) {
                    customerSignature = new CustomerSignature();
                }
            } else {
                customerSignature = new CustomerSignature();
            }
            customerSignature.setFile(signatureFile.getBytes());
            customerSignature.setType(signatureFile.getContentType());
            customerSignature.setCustomer(customer);
            this.customerSignatureRepository.save(customerSignature);
        }

        return customer.getCustomerId();

    }

    private Customer getCustomer(Customer customer, CustomerDto customerDto) {

        Customer tempCustomer = customerDto.getCustomer();

        customer.setCustomerCode(tempCustomer.getCustomerCode());
        customer.setCustomerName(tempCustomer.getCustomerName());
        customer.setCustomerGroup(tempCustomer.getCustomerGroup());
        customer.setContactNo(tempCustomer.getContactNo());
        customer.setAddress(tempCustomer.getAddress());
        customer.setSssNo(tempCustomer.getSssNo());
        customer.setHdmfNo(tempCustomer.getHdmfNo());
        customer.setPhicNo(tempCustomer.getPhicNo());
        customer.setTinNo(tempCustomer.getTinNo());
        customer.setBloodType(tempCustomer.getBloodType());
        customer.setErContactPerson(tempCustomer.getErContactPerson());
        customer.setErContactNo(tempCustomer.getErContactNo());
        customer.setErContactAddress(tempCustomer.getErContactAddress());

        return customer;

    }

}
