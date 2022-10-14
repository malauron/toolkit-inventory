package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface ButcheryProductionItemRepository extends JpaRepository<ButcheryProductionItem, Long> {

    @Query(value = "SELECT b FROM ButcheryProductionItem  b WHERE b.butcheryProduction = :butcheryProduction ORDER BY b.item.itemName")
    Set<ButcheryProductionItem> findByButcheryProductionOrderByItemName(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionItem  b WHERE b.butcheryProduction = :butcheryProduction ORDER BY b.item.itemId")
    Set<ButcheryProductionItem> findByButcheryProductionOrderByItemId(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b.* FROM butchery_production_items b " +
            "LEFT OUTER JOIN butchery_productions a on a.butchery_production_id = b.butchery_production_id " +
            "WHERE b.barcode = :barcode AND b.item_id = :itemId AND a.warehouse_id = :warehouseId AND b.version = 0 " +
            "ORDER BY b.butchery_production_item_id ASC LIMIT 1", nativeQuery = true)
    Optional<ButcheryProductionItem> findFirstByBarcodeAndItemAndWarehouse(String barcode, Long itemId, Long warehouseId);

    @Lock(LockModeType.OPTIMISTIC)
//    @Query(value = "SELECT b FROM ButcheryProductionItem b " +
//            "WHERE AND b.itemStatus = 'Available' b.barcode = :barcode AND b.item = :item " +
//            "AND b.butcheryProduction.warehouse = :warehouse ORDER BY b.butcheryProductionItemId ASC ")
    Optional<ButcheryProductionItem> findFirstByBarcodeAndItem(String barcode, Item item, Warehouse warehouse);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryProductionItem> findByButcheryProductionItemId(Long id);

}
