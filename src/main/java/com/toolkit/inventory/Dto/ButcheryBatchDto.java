package com.toolkit.inventory.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Domain.ButcheryBatchDetail;
import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import com.toolkit.inventory.Domain.VendorWarehouse;
import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryBatchDto {
    private Long butcheryBatchId;
    private Date dateReceived;
    private VendorWarehouse vendorWarehouse;
    private String remarks;
    private String batchStatus;
    private Boolean isOpen;
    private Boolean hasInventory;
    private User createdBy;
    private Set<ButcheryBatchDetail> butcheryBatchDetails;
    private Set<ButcheryBatchInventory> butcheryBatchInventories;
    private ButcheryBatch butcheryBatch;
    private Date dateCreated;
}
