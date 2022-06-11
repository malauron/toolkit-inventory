package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CustomerCharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerChargeRepository extends JpaRepository<CustomerCharge, Long> {
}
