package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ButcheryBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ButcheryBatchRepository  extends JpaRepository<ButcheryBatch, Long> {
}
