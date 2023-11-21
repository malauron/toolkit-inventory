package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Projection.ButcheryReleasingSummaryView;
import com.toolkit.inventory.Projection.ButcheryReleasingView;
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

@RepositoryRestResource(excerptProjection = ButcheryReleasingView.class)
public interface ButcheryReleasingRepository extends JpaRepository<ButcheryReleasing, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ButcheryReleasing> findByButcheryReleasingId(Long id);

//    @Query(value = "SELECT SUM(b.total_amount) AS totalAmount, SUM(b.total_weight_kg) AS totalWeightKg, " +
//            "DATE(b.date_created) AS dateCreated FROM butchery_releasings b " +
//            "WHERE b.warehouse_id = :warehouseId " +
//            "GROUP BY dateCreated " +
//            "HAVING dateCreated >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
//            "ORDER BY dateCreated",
//            nativeQuery = true)
    @Query(value = "SELECT b.totalAmount AS totalAmount, b.totalWeightKg AS totalWeightKg, b.dateCreated AS dateCreated  " +
        "FROM butchery_releasing_summary_view_30days b " +
        "WHERE b.warehouse_id = :warehouseId ",
        nativeQuery = true)
    Set<ButcheryReleasingSummaryView> getButcheryReceivingSummaryByWarehouseId(Long warehouseId);

    @Query(value = "SELECT SUM(b.totalAmount) AS totalAmount FROM ButcheryReleasingItem b " +
                   "WHERE b.butcheryReleasing = :butcheryReleasing")
    BigDecimal getTotalAmount(ButcheryReleasing butcheryReleasing);

    @Query(value = "SELECT SUM(b.releasedWeightKg) AS totalWeightKg FROM ButcheryReleasingItem b " +
            "WHERE b.butcheryReleasing = :butcheryReleasing")
    BigDecimal getTotalWeightKg(ButcheryReleasing butcheryReleasing);

    @Query(value = "SELECT b FROM ButcheryReleasing b " +
                   "WHERE (CONCAT(b.butcheryReleasingId,'') LIKE %:butcheryReleasingId% " +
                   "OR b.destinationWarehouse.warehouseName LIKE %:destinationWhse% " +
                   "OR b.warehouse.warehouseName LIKE %:warehouseName%) " +
                   "AND b.releasingStatus IN :releasingStatus " +
                   "ORDER BY b.butcheryReleasingId DESC")
    Page<ButcheryReleasing> findByCustomParam(
            @RequestParam("butcheryReleasingId") String butcheryReleasingId,
            @RequestParam("warehouseName") String warehouseName,
            @RequestParam("destinationWhse") String destinationWhse,
            @RequestParam("releasingStatus") Set<String> releasingStatus,
            Pageable pageable);

}
