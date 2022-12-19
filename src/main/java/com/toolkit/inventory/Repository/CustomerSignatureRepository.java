package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
public interface CustomerSignatureRepository extends JpaRepository<CustomerSignature, Long> {

  CustomerSignature findByCustomer(Customer customer);

}
