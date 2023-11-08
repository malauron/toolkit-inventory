package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Domain.Vendor;
import com.toolkit.inventory.Domain.VendorWarehouse;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryReceivingDto {
    private Long butcheryReceivingId;
    private Warehouse warehouse;
    private VendorWarehouse vendorWarehouse;
    private String referenceCode;
    private Set<ButcheryReceivingItem> butcheryReceivingItems;
    private ButcheryReceivingItem butcheryReceivingItem;
    private BigDecimal totalKg;
    private String receivingStatus;
    private Date dateCreated;
    private String errorDescription;
}
