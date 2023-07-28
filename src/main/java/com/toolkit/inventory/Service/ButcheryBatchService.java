package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.ButcheryBatchDto;

public interface ButcheryBatchService {
    ButcheryBatchDto getButcheryBatch(Long butcheryBatchId);
    ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto saveButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto saveButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto deleteButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto deleteButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto updateBatchStatus(ButcheryBatchDto batchDto);
}
