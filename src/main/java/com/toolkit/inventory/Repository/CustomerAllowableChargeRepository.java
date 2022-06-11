package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CustomerAllowableCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface CustomerAllowableChargeRepository  extends JpaRepository<CustomerAllowableCharge, Long> {

  CustomerAllowableCharge findByCustomerIdAndChargeTypeId(@RequestParam Long customerId, @RequestParam Long chargeTypeId);

}
