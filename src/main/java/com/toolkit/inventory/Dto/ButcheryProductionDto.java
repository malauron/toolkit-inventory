package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Projection.ButcheryProductionSourceShortView;
import com.toolkit.inventory.Projection.ButcheryProductionSourceView;
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
    private ButcheryBatch butcheryBatch;
    private Set<ButcheryProduction> butcheryProductions;
    private Set<ButcheryProductionItem> butcheryProductionItems;
    private Set<ButcheryProductionSource> butcheryProductionSources;
    private Set<ButcheryProductionSourceView> butcheryProductionSourceViews;
    private ButcheryProductionItem butcheryProductionItem;
    private ButcheryProductionSource butcheryProductionSource;
    private ButcheryProductionSourceView butcheryProductionSourceView;
    private Set<ButcheryProductionSourceShortView> butcheryProductionSourceShortViews;
    private BigDecimal totalProducedWeightKg;
    private String productionStatus;
    private Date dateCreated;
}
