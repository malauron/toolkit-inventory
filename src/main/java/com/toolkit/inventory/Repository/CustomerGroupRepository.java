package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Long> {

  @Query(value = "SELECT c FROM CustomerGroup c ORDER BY c.customerGroupName")
  List<CustomerGroup> findAll();

}
