package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Dto.ProjectUnitDto;
import com.toolkit.inventory.Projection.ButcheryReleasingSummaryView;

import java.util.Set;

public interface ProjectUnitService {
    ProjectUnitDto getUnit(Long unitId);
    ProjectUnit save(ProjectUnitDto projectUnitDto);
}
