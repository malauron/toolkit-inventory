package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Dto.ButcheryProductionDto;

public interface ButcheryProductionService {
    ButcheryProductionDto getButcheryProduction(Long butcheryProductionId);
    ButcheryProduction save(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setButcheryProduction(ButcheryProductionDto butcheryProductionDto);
    ButcheryProductionDto setProductionStatus(ButcheryProductionDto butcheryProductionDto);
}
