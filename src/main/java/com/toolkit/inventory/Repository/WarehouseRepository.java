package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
