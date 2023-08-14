package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemCost;
import com.toolkit.inventory.Domain.Warehouse;
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

@RepositoryRestResource
public interface ItemCostRepository extends JpaRepository<ItemCost, Long> {


    @Modifying
    @Query(value = "UPDATE ItemCost i SET i.qty = i.qty + :qty, " +
                    "i.weightKg = i.weightKg + :weightKg, i.version = i.version + 1, " +
                    "i.cost = CASE WHEN (i.cost > 0) THEN ((i.cost + :cost)/2) ELSE (:cost) END " +
                    "WHERE i.item = :item AND i.warehouse = :warehouse")
    void setQtyCost(BigDecimal qty,
                    BigDecimal weightKg,
                    BigDecimal cost,
                    Item item,
                    Warehouse warehouse);

    @Modifying
    @Query(value = "UPDATE ItemCost i SET i.qty = i.qty + :qty, i.weightKg = i.weightKg + :weightKg, " +
                   "i.version = i.version + 1 " +
                   "WHERE i.itemCostId = :itemCostId")
    void setQty(BigDecimal qty, BigDecimal weightKg, Long itemCostId);

    Optional<ItemCost> findByItemAndWarehouse(Item item, Warehouse warehouse);

    @Query(value = "SELECT i FROM ItemCost i WHERE i.warehouse.warehouseId = :warehouseId ORDER BY i.item.itemName")
    Set<ItemCost> findByWarehouseId(Long warehouseId);

    @Query(value = "SELECT i FROM ItemCost i WHERE i.warehouse.warehouseId = :warehouseId " +
                   "AND i.item.itemName LIKE %:itemName% " +
                   "AND i.version > 0 " +
                   "ORDER BY i.item.itemName")
    Page<ItemCost> findByWarehouseIdAndItemName(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);


}
