package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.CustomerGroup;
import com.toolkit.inventory.Repository.CustomerGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerGroupServiceImp implements CustomerGroupService {

  private CustomerGroupRepository customerGroupRepository;

  public CustomerGroupServiceImp(CustomerGroupRepository customerGroupRepository) {
    this.customerGroupRepository = customerGroupRepository;
  }

  @Override
  public List<CustomerGroup> findAll() {

    return this.customerGroupRepository.findAll();

  }

}
