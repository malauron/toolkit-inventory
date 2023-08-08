package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryProductionDto;
import com.toolkit.inventory.Projection.*;

import java.util.Set;

public interface ButcheryProductionService {

    ButcheryProductionDto getButcheryProduction(Long butcheryProductionId);
    ButcheryProductionDto save(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setButcheryProduction(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setProductionStatus(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto deleteButcheryProductionItem(ButcheryProductionItem butcheryProductionItem);
    ButcheryProductionDto putButcheryProductionItem(ButcheryProductionItem butcheryProductionItem);
    ButcheryProductionDto deleteButcheryProductionSource(ButcheryProductionSource butcheryProductionSource);
    ButcheryProductionDto putButcheryProductionSource(ButcheryProductionSource butcheryProductionSource);
//    ButcheryProductionDto findByButcheryReceivingId(Long butcheryReceivingId);

//    Set<ButcheryProductionSourceAggregatedView> unitTest(Long id);
    Set<ButcheryProductionItemAggregatedView> unitTest2(Long id);
    Set<ButcheryProductionAggregatedView> unitTest3();
//    Set<ButcheryProductionSourceShortView> unitTest4();

}
