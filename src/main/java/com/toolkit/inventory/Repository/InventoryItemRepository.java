package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.InventoryItem;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Projection.InventoryItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = InventoryItemView.class)
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<InventoryItem> findByInventoryItemId(Long id);

    @Modifying
    @Query(value = "UPDATE InventoryItem i SET i.purchasedQty = i.purchasedQty + :qty, i.cost = :cost " +
            "WHERE i.item = :item AND i.warehouse = :warehouse")
    void setPurchasedQtyCost(BigDecimal qty, BigDecimal cost, Item item, Warehouse warehouse);

    @Modifying
    @Query(value = "UPDATE InventoryItem i SET i.endingQty = i.endingQty + :qty " +
            "WHERE i.inventoryItemId = :inventoryItemId")
    void setEndingQty(BigDecimal qty, Long inventoryItemId);

    @Modifying
    @Query(value = "UPDATE InventoryItem i SET i.beginningQty = 0, i.purchasedQty = 0 " +
            "WHERE  i.warehouse.warehouseId = :warehouseId")
    void setBeginningPurchaseQty(Long warehouseId);

    @Modifying
    @Query(value = "UPDATE InventoryItem i SET i.beginningQty = i.endingQty " +
            "WHERE i.warehouse.warehouseId = :warehouseId")
    void setBeginningQty(Long warehouseId);

    @Modifying
    @Query(value = "UPDATE InventoryItem i SET i.beginningQty = i.endingQty, " +
            "i.purchasedQty = 0, i.endingQty = 0 " +
            "WHERE i.warehouse.warehouseId = :warehouseId")
    void setQty(Long warehouseId);

    @Query(value = "SELECT i FROM InventoryItem i WHERE i.warehouse.warehouseId = :warehouseId " +
            "AND (i.item.itemName LIKE %:itemName% OR i.item.itemCode LIKE %:itemName%) ORDER BY i.item.itemName")
    Page<InventoryItem> findByCustomParam(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);

    @Query(value = "SELECT i FROM InventoryItem i WHERE (i.beginningQty > 0 OR i.purchasedQty > 0 " +
            "OR i.endingQty > 0) AND i.warehouse.warehouseId = :warehouseId " +
            "ORDER BY i.item.itemName")
    Set<InventoryItem> findAllByWarehouseIdWithQty(Long warehouseId);

}
