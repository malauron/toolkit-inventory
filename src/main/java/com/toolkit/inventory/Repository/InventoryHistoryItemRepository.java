package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.InventoryHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InventoryHistoryItemRepository extends JpaRepository<InventoryHistoryItem, Long> {

}
