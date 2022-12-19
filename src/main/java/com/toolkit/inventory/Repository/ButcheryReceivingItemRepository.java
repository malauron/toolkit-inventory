package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface ButcheryReceivingItemRepository extends JpaRepository<ButcheryReceivingItem, Long> {

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemName")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemName(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceiving = :butcheryReceiving ORDER BY b.item.itemId")
    Set<ButcheryReceivingItem> findByButcheryReceivingOrderByItemId(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.butcheryReceivingItemId = :butcheryReceivingItemId " +
            "AND b.isAvailable=TRUE")
    Optional<ButcheryReceivingItemView> findByIdAndIsAvailable(Long butcheryReceivingItemId);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.isAvailable=TRUE AND b.butcheryReceiving.warehouse = :warehouse")
    Set<ButcheryReceivingItemView> findByWarehouseAndIsAvailable(Warehouse warehouse);

    @Query(value = "SELECT b FROM ButcheryReceivingItem  b WHERE b.isAvailable=TRUE " +
                   "AND b.item.itemName LIKE %:itemName% " +
                   "AND b.butcheryReceiving.warehouse.warehouseId = :warehouseId")
    Page<ButcheryReceivingItem> findByWarehouseAndIsAvailablePageable(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);

    @Modifying
    @Query(value = "UPDATE ButcheryReceivingItem b SET b.usedQty = b.usedQty + :usedQty, " +
            "b.isAvailable = CASE WHEN (b.usedQty >= b.receivedQty) THEN FALSE ELSE b.isAvailable END " +
            "WHERE b.butcheryReceivingItemId = :id")
    void setUsedQty(BigDecimal usedQty, Long id);
}
