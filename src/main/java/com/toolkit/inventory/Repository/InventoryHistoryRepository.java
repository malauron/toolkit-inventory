package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.InventoryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Long> {

    @Query(value ="SELECT i FROM InventoryHistory i WHERE i.warehouse.warehouseId = :warehouseId " +
            "ORDER BY i.inventoryHistoryId DESC")
    Page<InventoryHistory> findByWarehouseId(
            @RequestParam("warehouseId") Long warehouseId,
            Pageable pageable);

}
