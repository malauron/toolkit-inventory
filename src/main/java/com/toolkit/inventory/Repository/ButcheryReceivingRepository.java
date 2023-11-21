package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Projection.ButcheryReceivingView;
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

@RepositoryRestResource(excerptProjection = ButcheryReceivingView.class)
public interface ButcheryReceivingRepository extends JpaRepository<ButcheryReceiving, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryReceiving> findByButcheryReceivingId(Long id);

    @Query(value = "SELECT SUM(b.receivedWeightKg) AS totalKg FROM ButcheryReceivingItem b " +
                   "WHERE b.butcheryReceiving = :butcheryReceiving")
    BigDecimal getTotalKg(ButcheryReceiving butcheryReceiving);

    @Query(value = "SELECT b FROM ButcheryReceiving b " +
                   "WHERE (CONCAT(b.butcheryReceivingId,'') LIKE %:butcheryReceivingId% " +
                   "OR b.vendorWarehouse.vendorWarehouseName LIKE %:vendorName%) " +
                   "AND b.receivingStatus IN :receivingStatus " +
                   "AND b.warehouse.warehouseId = :warehouseId " +
                   "ORDER BY b.butcheryReceivingId DESC")
    Page<ButcheryReceiving> findByCustomParam(
            @RequestParam("butcheryReceivingId") String butcheryReceivingId,
            @RequestParam("vendorName") String vendorName,
            @RequestParam("receivingStatus") Set<String> receivingStatus,
            @RequestParam("warehouseId") Long warehouseId,
            Pageable pageable);

}
