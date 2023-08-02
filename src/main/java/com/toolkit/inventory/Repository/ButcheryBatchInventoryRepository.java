package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ButcheryBatchInventoryRepository extends JpaRepository<ButcheryBatchInventory, Long> {

}
