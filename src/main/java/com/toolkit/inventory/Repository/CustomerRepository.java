package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Projection.CustomerView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = CustomerView.class)
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByCustomerNameContainingOrderByCustomerName(@RequestParam("customerName")  String customerName, Pageable pageable);

}
