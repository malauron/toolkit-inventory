package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.InventoryHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource
public interface InventoryHistoryItemRepository extends JpaRepository<InventoryHistoryItem, Long> {

    @Query(value = "SELECT i FROM InventoryHistoryItem i " +
            "WHERE i.inventoryHistory.inventoryHistoryId = :inventoryHistoryId " +
            "ORDER BY i.item.itemName")
    Set<InventoryHistoryItem> findByInventoryHistoryId(Long inventoryHistoryId);

}
