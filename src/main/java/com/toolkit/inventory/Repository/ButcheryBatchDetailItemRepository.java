package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatchDetailItem;
import com.toolkit.inventory.Projection.ButcheryBatchDetailItemAggregatedView;
import com.toolkit.inventory.Projection.ButcheryBatchDetailItemInventoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface ButcheryBatchDetailItemRepository extends JpaRepository<ButcheryBatchDetailItem, Long> {

    @Query(value = "SELECT b.butcheryBatchDetail AS butcheryBatchDetail, " +
            "SUM(b.documentedWeightKg) AS totalDocumentedWeightKg,  " +
            "SUM(b.receivedWeightKg) AS totalReceivedWeightKg " +
            "FROM ButcheryBatchDetailItem b " +
            "WHERE b.butcheryBatchDetail.butcheryBatchDetailId = :butcheryBatchDetailId")
    Optional<ButcheryBatchDetailItemAggregatedView> getBatchDetailById(Long butcheryBatchDetailId);

    @Query(value = "SELECT b.item AS item, SUM(b.receivedWeightKg) AS receivedWeightKg,  " +
            "SUM(b.baseQty * b.receivedQty) AS receivedQty " +
            "FROM ButcheryBatchDetailItem AS b " +
            "WHERE b.butcheryBatchDetail.butcheryBatch.butcheryBatchId =:butcheryBatchId " +
            "GROUP BY b.item")
    Set<ButcheryBatchDetailItemInventoryView> getInventories(Long butcheryBatchId);

}
