package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ButcheryBatchDetailRepository extends JpaRepository<ButcheryBatchDetail, Long> {
}
