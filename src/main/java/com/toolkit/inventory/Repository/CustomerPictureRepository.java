package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.CustomerPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerPictureRepository extends JpaRepository<CustomerPicture, Long> {

  CustomerPicture findByCustomer(Customer customer);

}
