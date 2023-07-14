package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ButcheryBatchRepository  extends JpaRepository<ButcheryBatch, Long> {
}
