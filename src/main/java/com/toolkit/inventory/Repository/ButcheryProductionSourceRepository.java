package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Projection.ButcheryProductionSourceAggregatedView;
import com.toolkit.inventory.Projection.ButcheryProductionSourceShortView;
import com.toolkit.inventory.Projection.ButcheryProductionSourceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryProductionSourceView.class)
public interface ButcheryProductionSourceRepository extends JpaRepository<ButcheryProductionSource, Long> {

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction ")
    Set<ButcheryProductionSource> findByButcheryProductionOrderByButcheryReceivingItemId(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProduction = :butcheryProduction ")
    Set<ButcheryProductionSourceView> findByButcheryProductionOrderByButcheryReceivingItemIdView(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProductionSource  b WHERE b.butcheryProductionSourceId = :butcheryProductionSourceId")
    Optional<ButcheryProductionSourceView> findByIdView(Long butcheryProductionSourceId);

//    @Query(value = "SELECT b.butcheryProduction AS butcheryProduction FROM ButcheryProductionSource b " +
//            "WHERE b.butcheryReceivingItem.butcheryReceiving.butcheryReceivingId = :butcheryReceivingId " +
//            "AND b.butcheryProduction.productionStatus = 'Posted' " +
//            "GROUP BY b.butcheryProduction")
//    Set<ButcheryProductionSourceShortView> findByButcheryReceivingId(Long butcheryReceivingId);

//    @Query(value = "SELECT b.butcheryReceivingItem AS butcheryReceivingItem, SUM(b.requiredQty) AS requiredQty " +
//            "FROM ButcheryProductionSource b WHERE b.butcheryProduction = :butcheryProduction " +
//            "AND b.butcheryProduction.productionStatus = 'Posted' ")
//    Set<ButcheryProductionSourceAggregatedView> searchByButcheryProduction(ButcheryProduction butcheryProduction);


}
