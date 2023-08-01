package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Projection.ButcheryBatchView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(excerptProjection = ButcheryBatchView.class)
public interface ButcheryBatchRepository  extends JpaRepository<ButcheryBatch, Long> {

    @Query(value = "SELECT b FROM ButcheryBatch b ORDER BY b.butcheryBatchId DESC")
    Page<ButcheryBatch> findAllBatches(Pageable pageable);

    @Query(value = "SELECT b FROM ButcheryBatch  b " +
            "WHERE b.vendorWarehouse.vendorWarehouseName like %:searchDesc% " +
            "OR b.remarks like %:searchDesc% " +
            "ORDER BY b.butcheryBatchId DESC")
    Page<ButcheryBatch> findByWarehouseRemarks(
            @RequestParam("searchDesc") String searchDesc,
            Pageable pageable);

}
