package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatchDetail;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryBatchDetailItemAggregatedView", types = { ButcheryBatchDetail.class })
public interface ButcheryBatchDetailItemAggregatedView {
    ButcheryBatchDetail getButcheryBatchDetail();
    BigDecimal getTotalDocumentedWeightKg();
    BigDecimal getTotalReceivedWeightKg();
}
