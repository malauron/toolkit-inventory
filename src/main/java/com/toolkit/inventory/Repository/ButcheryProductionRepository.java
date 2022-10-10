package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Projection.ButcheryProductionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryProductionView.class)
public interface ButcheryProductionRepository extends JpaRepository<ButcheryProduction, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryProduction> findByButcheryProductionId(Long id);

    @Query(value = "SELECT b FROM ButcheryProduction b " +
                   "WHERE b.butcheryProductionId LIKE :butcheryProductionId " +
                   "AND b.productionStatus IN :productionStatus " +
                   "ORDER BY b.butcheryProductionId DESC")
    Page<ButcheryProduction> findByCustomParam(
                   @RequestParam("butcheryProductionId") Long butcheryProductionId,
                   @RequestParam("productionStatus") Set<String> productionStatus,
                   Pageable pageable);

}
