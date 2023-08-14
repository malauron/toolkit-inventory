package com.toolkit.inventory.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Projection.ButcheryBatchInventorySummary;
import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryBatchDto {

    private ButcheryBatch butcheryBatch;
    private ButcheryBatchDetail butcheryBatchDetail;
    private ButcheryBatchDetailItem butcheryBatchDetailItem;
    private Set<ButcheryBatchDetail> butcheryBatchDetails;
    private Set<ButcheryBatchInventory> butcheryBatchInventories;
    private Page<ButcheryBatchInventorySummary> butcheryBatchInventorySummaries;
    private String error;
}
