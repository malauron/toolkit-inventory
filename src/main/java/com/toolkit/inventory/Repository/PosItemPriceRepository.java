package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.PosItemPrice;
import com.toolkit.inventory.Projection.PosItemPriceView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = PosItemPriceView.class)
public interface PosItemPriceRepository extends JpaRepository<PosItemPrice, Long> {

    @Query(value = "SELECT p FROM PosItemPrice p " +
                    "WHERE p.warehouse.warehouseId = :warehouseId " +
                    "AND p.item.itemId = :itemId")
    Optional<PosItemPriceView> findByWarehouseIdAndItemId(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemId") Long itemId
    );

    @Query(value = "SELECT p FROM PosItemPrice p " +
                    "WHERE p.warehouse.warehouseId = :warehouseId " +
                    "AND p.item.itemName LIKE %:itemName% " +
                    "ORDER BY p.item.itemName")
    Page<PosItemPrice> findByWarehouseIdAndItemName(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("itemName") String itemName,
            Pageable pageable);

}
