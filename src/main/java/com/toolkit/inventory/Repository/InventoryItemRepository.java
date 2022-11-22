package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Projection.InventoryItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = InventoryItemView.class)
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    @Query(value = "SELECT i FROM InventoryItem i WHERE i.warehouse.warehouseId = :warehouseId " +
            "AND i.item.itemName LIKE %:itemName% ORDER BY i.item.itemName")
    Page<InventoryItem> findByCustomParam(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);

}
