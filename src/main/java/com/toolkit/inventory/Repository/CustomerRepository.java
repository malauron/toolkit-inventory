package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
