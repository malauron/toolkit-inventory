package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.ButcheryBatchDto;
import org.springframework.data.domain.Page;

public interface ButcheryBatchService {
    ButcheryBatchDto getButcheryBatch(Long butcheryBatchId);
    Page getButcheryBatchInventorySummary(int page, int size, Long vendorWarehouseId, String itemName);
    ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto saveButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto saveButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto deleteButcheryBatchDetail(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto deleteButcheryBatchDetailItem(ButcheryBatchDto butcheryBatchDto);
    ButcheryBatchDto updateBatchStatus(ButcheryBatchDto batchDto);
}
