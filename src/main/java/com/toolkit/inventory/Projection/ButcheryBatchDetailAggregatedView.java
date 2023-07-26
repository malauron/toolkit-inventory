package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatchDetail;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "butcheryBatchDetailAggregatedView", types = { ButcheryBatchDetail.class })
public interface ButcheryBatchDetailAggregatedView {
    ButcheryBatchDetail getButcheryBatchDetail();
    BigDecimal getTotalRequiredWeightKg();
    BigDecimal getTotalReceivedWeightKg();
}
