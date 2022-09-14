package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Projection.WarehouseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RepositoryRestResource(excerptProjection = WarehouseView.class)
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

  Page<Warehouse> findByWarehouseNameContainingOrderByWarehouseName(
          @RequestParam("warehouseName") String warehouseName,
          Pageable pageable
  );
}
