package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Projection.ButcheryProductionSourceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.LockModeType;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface ButcheryProductionSourceRepository extends JpaRepository<ButcheryProductionSource, Long> {

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction " +
            " ORDER BY b.butcheryReceivingItem.butcheryReceivingItemId")
    Set<ButcheryProductionSource> findByButcheryProductionOrderByButcheryReceivingItemId(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction " +
            " ORDER BY b.butcheryReceivingItem.butcheryReceivingItemId")
    Set<ButcheryProductionSourceView> findByButcheryProductionOrderByButcheryReceivingItemIdView(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProductionSourceId = :butcheryProductionSourceId")
    Optional<ButcheryProductionSourceView> findByIdView(Long butcheryProductionSourceId);

//    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction ORDER BY b.item.itemId")
//    Set<ButcheryProductionSource> findByButcheryProductionOrderByItemId(ButcheryProduction butcheryProduction);

//    @Lock(LockModeType.OPTIMISTIC)
//    Optional<ButcheryProductionSource> findFirstByBarcodeAndItemAndWarehouseAndIsAvailableIsTrue(String barcode, Item item, Warehouse warehouse);

}
