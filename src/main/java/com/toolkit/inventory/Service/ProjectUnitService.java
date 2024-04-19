package com.toolkit.inventory.Service;

import com.toolkit.inventory.Dto.ProjectUnitDto;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

public interface ProjectUnitService {
    ProjectUnitDto getUnit(Long unitId);
    ProjectUnitDto save(ProjectUnitDto projectUnitDto) throws Exception;
}
