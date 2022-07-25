package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CustomerCharge;
import com.toolkit.inventory.Repository.CustomerChargeRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerChargeServiceImp implements CustomerChargeService {

  private CustomerChargeRepository customerChargeRepository;

  public CustomerChargeServiceImp(CustomerChargeRepository customerChargeRepository) {
    this.customerChargeRepository = customerChargeRepository;
  }

  @Override
  public void save(CustomerCharge customerCharge) {
    customerChargeRepository.save(customerCharge);
  }
}
