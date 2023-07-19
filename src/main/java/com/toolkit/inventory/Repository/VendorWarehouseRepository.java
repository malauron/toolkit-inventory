package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.VendorWarehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = VendorWarehouse.class)
public interface VendorWarehouseRepository extends JpaRepository<VendorWarehouse, Long> {

    Optional<VendorWarehouse> findByVendorWarehouseId(@RequestParam Long id);

    Page<VendorWarehouse> findByVendorWarehouseNameContainingOrderByVendorWarehouseName(
            @RequestParam("vendorWarehouseName") String vendorWarehouseName,
            Pageable pageable
    );
}
