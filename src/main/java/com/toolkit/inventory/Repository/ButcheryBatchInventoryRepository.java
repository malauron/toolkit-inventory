package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Projection.ButcheryBatchInventorySummary;
import com.toolkit.inventory.Projection.ButcheryBatchInventoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryBatchInventoryView.class)
public interface ButcheryBatchInventoryRepository extends JpaRepository<ButcheryBatchInventory, Long> {

    @Query(value = "SELECT i FROM ButcheryBatchInventory i " +
                   "WHERE i.butcheryBatch.butcheryBatchId = :butcheryBatchId " +
                   "AND i.item.itemId = :itemId")
    Optional<ButcheryBatchInventory> findByItemIdAndButcheryBatchId(
            @RequestParam("butcheryBatchId") Long butcheryBatchId,
            @RequestParam("itemId") Long itemId);

    @Query(value = "SELECT i.item FROM ButcheryBatchInventory i " +
                   "WHERE i.butcheryBatch.butcheryBatchId = :butcheryBatchId " +
                   "AND i.item.itemName LIKE %:searchDesc%  " +
                   "ORDER BY i.item.itemName ")
    Page<Item> findItemByButcheryBatchId(
            @RequestParam("butcheryBatchId") Long butcheryBatchId,
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);

    @Query(value = "SELECT i.item AS item, SUM(i.receivedQty) AS receivedQty, " +
            "SUM(i.receivedWeightKg) AS receivedWeightKg, SUM(i.remainingQty) AS remainingQty, " +
            "SUM(i.remainingWeightKg) as remainingWeightKg " +
            "FROM ButcheryBatchInventory i " +
            "WHERE i.butcheryBatch.butcheryBatchId = :butcheryBatchId " +
            "GROUP BY i.item " +
            "ORDER BY i.item.itemName")
    Set<ButcheryBatchInventory> getInventorySummaryByBatchId(
            @RequestParam("butcheryBatchId") Long butcheryBatchId);

    @Query(value = "SELECT i.item AS item, SUM(i.receivedQty) AS receivedQty, " +
            "SUM(i.receivedWeightKg) AS receivedWeightKg, SUM(i.remainingQty) AS remainingQty, " +
            "SUM(i.remainingWeightKg) as remainingWeightKg " +
            "FROM ButcheryBatchInventory i " +
            "WHERE i.butcheryBatch.vendorWarehouse.vendorWarehouseId = :vendorWarehouseId " +
            "AND i.item.itemName like %:itemName% " +
            "GROUP BY i.item " +
            "ORDER BY i.item.itemName")
    Page<ButcheryBatchInventorySummary> getInventorySummaryByVendorWarehouse(
            @RequestParam("vendorWarehouseId") Long vendorWarehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);

    @Modifying
    @Query(value = "UPDATE ButcheryBatchInventory i SET i.remainingQty = i.remainingQty + :receivedQty, " +
            "i.remainingWeightKg = i.remainingWeightKg + :receivedWeightKg " +
            "WHERE i.butcheryBatchInventoryId = :butcheryBatchInventoryId")
    void updateQuantities(
            BigDecimal receivedQty,
            BigDecimal receivedWeightKg,
            Long butcheryBatchInventoryId);
}
