package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryBatchDetail;
import com.toolkit.inventory.Domain.ButcheryBatchInventory;
import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryBatchDto {
    private Long butcheryBatchId;
    private String remarks;
    private Date dateReceived;
    private Boolean isOpen;
    private Boolean hasInventory;
    private User user;
    private Set<ButcheryBatchDetail> butcheryBatchDetails;
    private Set<ButcheryBatchInventory> butcheryBatchInventories;
    private Date dateCreated;
}
