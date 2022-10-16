package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryReleasingDto {
    private Long butcheryReleasingId;
    private Warehouse warehouse;
    private Customer customer;
    private Set<ButcheryReleasingItem> butcheryReleasingItems;
    private ButcheryReleasingItem butcheryReleasingItem;
    private BigDecimal totalAmount;
    private String releasingStatus;
    private Date dateCreated;
    private String errorDescription;
}
