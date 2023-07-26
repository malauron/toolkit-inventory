package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatchDetailItem;
import com.toolkit.inventory.Projection.ButcheryBatchDetailAggregatedView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ButcheryBatchDetailItemRepository extends JpaRepository<ButcheryBatchDetailItem, Long> {

    @Query(value = "SELECT b.butcheryBatchDetail AS butcheryBatchDetail, " +
            "SUM(b.requiredWeightKg) AS totalRequiredWeightKg,  " +
            "SUM(b.receivedWeightKg) AS totalReceivedWeightKg " +
            "FROM ButcheryBatchDetailItem b " +
            "WHERE b.butcheryBatchDetail.butcheryBatchDetailId = :butcheryBatchDetailId")
    Optional<ButcheryBatchDetailAggregatedView> getBatchDetailById(Long butcheryBatchDetailId);

}
