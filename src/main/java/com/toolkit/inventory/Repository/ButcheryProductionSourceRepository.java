package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Projection.ButcheryProductionSourceView;
import com.toolkit.inventory.Projection.ButcheryProductionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryProductionSourceView.class)
//@RepositoryRestResource
public interface ButcheryProductionSourceRepository extends JpaRepository<ButcheryProductionSource, Long> {

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction " +
            " ORDER BY b.butcheryReceivingItem.butcheryReceivingItemId")
    Set<ButcheryProductionSource> findByButcheryProductionOrderByButcheryReceivingItemId(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction " +
            " ORDER BY b.butcheryReceivingItem.butcheryReceivingItemId")
    Set<ButcheryProductionSourceView> findByButcheryProductionOrderByButcheryReceivingItemIdView(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProductionSourceId = :butcheryProductionSourceId")
    Optional<ButcheryProductionSourceView> findByIdView(Long butcheryProductionSourceId);

    @Query(value = "SELECT b.butcheryProduction FROM ButcheryProductionSource b GROUP BY b.butcheryProduction")
    Set<ButcheryProductionView> findGroupedProduction();

//    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction ORDER BY b.item.itemId")
//    Set<ButcheryProductionSource> findByButcheryProductionOrderByItemId(ButcheryProduction butcheryProduction);

//    @Lock(LockModeType.OPTIMISTIC)
//    Optional<ButcheryProductionSource> findFirstByBarcodeAndItemAndWarehouseAndIsAvailableIsTrue(String barcode, Item item, Warehouse warehouse);

}
