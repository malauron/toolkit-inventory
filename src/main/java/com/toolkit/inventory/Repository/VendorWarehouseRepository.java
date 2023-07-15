package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.VendorWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VendorWarehouseRepository extends JpaRepository<VendorWarehouse, Long> {
}
