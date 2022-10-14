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

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryProductionItem> findFirstByBarcodeAndItemAndWarehouseAndIsAvailableIsTrue(String barcode, Item item, Warehouse warehouse);

}
