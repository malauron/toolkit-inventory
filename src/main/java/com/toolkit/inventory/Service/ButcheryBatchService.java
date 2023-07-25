package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.ButcheryBatchDto;

public interface ButcheryBatchService {
    ButcheryBatchDto getButcheryBatch(Long butcheryBatchId);
    ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto saveButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto);
}
