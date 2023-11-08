package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Projection.ButcheryProductionAggregatedView;
import com.toolkit.inventory.Projection.ButcheryProductionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryProductionView.class)
public interface ButcheryProductionRepository extends JpaRepository<ButcheryProduction, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryProduction> findByButcheryProductionId(Long id);

    @Query(value = "SELECT SUM(b.producedWeightKg) AS totalProducedWeightKg FROM ButcheryProductionItem b " +
                   "WHERE b.butcheryProduction = :butcheryProduction")
    BigDecimal getTotalProducedWeightKg(ButcheryProduction butcheryProduction);

    @Query(value = "SELECT b FROM ButcheryProduction b " +
                   "WHERE (CONCAT(b.butcheryProductionId,'') LIKE %:butcheryProductionId% " +
                   "OR b.warehouse.warehouseName like %:warehouseName%) " +
                   "AND b.productionStatus IN :productionStatus " +
                   "ORDER BY b.butcheryProductionId DESC")
    Page<ButcheryProduction> findByCustomParam(
                   @RequestParam("butcheryProductionId") String butcheryProductionId,
                   @RequestParam("warehouseName") String warehouseName,
                   @RequestParam("productionStatus") Set<String> productionStatus,
                   Pageable pageable);

    @Query(value = "SELECT b FROM ButcheryProduction b ORDER BY b.butcheryProductionId")
    Set<ButcheryProductionAggregatedView> searchButcheryProduction();

}
