package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Projection.ButcheryProductionSourceView;
import com.toolkit.inventory.Projection.ButcheryProductionView;
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
    private Set<ButcheryProductionSource> butcheryProductionSources;
    private Set<ButcheryProductionSourceView> butcheryProductionSourceViews;
    private ButcheryProductionItem butcheryProductionItem;
    private ButcheryProductionSource butcheryProductionSource;
    private ButcheryProductionSourceView butcheryProductionSourceView;
    private BigDecimal totalAmount;
    private String productionStatus;
    private Date dateCreated;

    private Set<ButcheryProductionView> butcheryProductionViews;
}
