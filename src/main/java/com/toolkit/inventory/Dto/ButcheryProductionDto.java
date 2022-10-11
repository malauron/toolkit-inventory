package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ButcheryProductionDto {
    private Long butcheryProductionId;
    private Warehouse warehouse;
    private Set<ButcheryProductionItem> butcheryProductionItems;
    private ButcheryProductionItem butcheryProductionItem;
    private BigDecimal totalAmount;
    private String productionStatus;
    private Date dateCreated;
}
