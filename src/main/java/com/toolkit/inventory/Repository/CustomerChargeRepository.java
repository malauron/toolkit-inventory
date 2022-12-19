package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CustomerCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
public interface CustomerChargeRepository extends JpaRepository<CustomerCharge, Long> {
}
