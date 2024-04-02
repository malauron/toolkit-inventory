package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Dto.ProjectUnitDto;
import com.toolkit.inventory.Repository.ProjectUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectUnitServiceImp implements ProjectUnitService {

    final ProjectUnitRepository unitRepository;

    @Autowired
    public ProjectUnitServiceImp(
            ProjectUnitRepository unitRepository
    ){
        this.unitRepository = unitRepository;
    }

    @Override
    public ProjectUnitDto getUnit(Long unitId) {

        ProjectUnitDto unitDto = new ProjectUnitDto();

        Optional<ProjectUnit> optUnit = this.unitRepository.findById(unitId);

        if (optUnit.isPresent()) {
            unitDto.setUnitId(optUnit.get().getUnitId());
            unitDto.setUnitCode(optUnit.get().getUnitCode());
            unitDto.setUnitDescription(optUnit.get().getUnitDescription());
            unitDto.setUnitPrice(optUnit.get().getUnitPrice());
            unitDto.setUnitClass(optUnit.get().getUnitClass());
            unitDto.setUnitStatus(optUnit.get().getUnitStatus());
            unitDto.setCurrentContract(optUnit.get().getCurrentContract());
        }

        return unitDto;

    }

    @Override
    public ProjectUnit save(ProjectUnitDto projectUnitDto) {
        return null;
    }
}
