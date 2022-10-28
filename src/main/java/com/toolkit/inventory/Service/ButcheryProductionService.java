package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Dto.ButcheryProductionDto;

public interface ButcheryProductionService {

    ButcheryProductionDto getButcheryProduction(Long butcheryProductionId);
    ButcheryProductionDto save(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setButcheryProduction(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setProductionStatus(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto deleteButcheryProductionItem(ButcheryProductionItem butcheryProductionItem);
    ButcheryProductionDto putButcheryProductionItem(ButcheryProductionItem butcheryProductionItem);
    ButcheryProductionDto deleteButcheryProductionSource(ButcheryProductionSource butcheryProductionSource);
    ButcheryProductionDto putButcheryProductionSource(ButcheryProductionSource butcheryProductionSource);

}
