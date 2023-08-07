package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Projection.ButcheryBatchView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Set;

@RepositoryRestResource(excerptProjection = ButcheryBatchView.class)
public interface ButcheryBatchRepository  extends JpaRepository<ButcheryBatch, Long> {

    @Query(value = "SELECT b FROM ButcheryBatch b ORDER BY b.butcheryBatchId DESC")
    Page<ButcheryBatch> findAllBatches(Pageable pageable);

    @Query(value = "SELECT b FROM ButcheryBatch  b " +
            "WHERE (b.vendorWarehouse.vendorWarehouseName like %:searchDesc% " +
            "OR b.vendor.vendorName like %:searchDesc% OR b.remarks like %:searchDesc%) " +
            "AND b.batchStatus IN :batchStatuses AND b.isOpen in :isOpen " +
            "ORDER BY b.butcheryBatchId DESC")
    Page<ButcheryBatch> findByCustomParams(
            @RequestParam("searchDesc") String searchDesc,
            @RequestParam("batchStatuses") Set<String> batchStatuses,
            @RequestParam("isOpen") Set<Boolean> isOpen,
            Pageable pageable);

}
