package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Project;
import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Domain.ProjectUnitStatus;
import com.toolkit.inventory.Dto.ProjectUnitDto;
import com.toolkit.inventory.Repository.ProjectRepository;
import com.toolkit.inventory.Repository.ProjectUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjectUnitServiceImp implements ProjectUnitService {

    final ProjectRepository projectRepository;
    final ProjectUnitRepository unitRepository;

    @Autowired
    public ProjectUnitServiceImp(
            ProjectRepository projectRepository,
            ProjectUnitRepository unitRepository
    ){
        this.projectRepository = projectRepository;
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
            unitDto.setReservationAmt(optUnit.get().getReservationAmt());
            unitDto.setUnitClass(optUnit.get().getUnitClass());
            unitDto.setUnitStatus(optUnit.get().getUnitStatus());
            unitDto.setCurrentContract(optUnit.get().getCurrentContract());
        }

        return unitDto;

    }

    @Override
    @Transactional
    public ProjectUnit save(ProjectUnitDto projectUnitDto) {

        ProjectUnit newUnit = new ProjectUnit();

        newUnit.setUnitCode(projectUnitDto.getUnitCode());
        newUnit.setUnitDescription(projectUnitDto.getUnitDescription());
        newUnit.setUnitPrice(projectUnitDto.getUnitPrice());
        newUnit.setReservationAmt(projectUnitDto.getReservationAmt());
        newUnit.setUnitStatus(ProjectUnitStatus.AVAILABLE);
        newUnit.setUnitClass(projectUnitDto.getUnitClass());

        Optional<Project> optProject = this.projectRepository.findById(projectUnitDto.getProject().getProjectId());
        if (optProject.isPresent()) {
            newUnit.setProject(optProject.get());
        }

        this.unitRepository.save(newUnit);

        return newUnit;
    }

}
