package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Project;
import com.toolkit.inventory.Domain.ProjectContractEquitySchedule;
import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Domain.ProjectUnitStatus;
import com.toolkit.inventory.Dto.ProjectUnitDto;
import com.toolkit.inventory.Repository.ProjectContractEquityScheduleRepository;
import com.toolkit.inventory.Repository.ProjectRepository;
import com.toolkit.inventory.Repository.ProjectUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectUnitServiceImp implements ProjectUnitService {

    final ProjectRepository projectRepository;
    final ProjectUnitRepository unitRepository;
    final ProjectContractEquityScheduleRepository equityScheduleRepository;

    @Autowired
    public ProjectUnitServiceImp(
            ProjectRepository projectRepository,
            ProjectUnitRepository unitRepository,
            ProjectContractEquityScheduleRepository equityScheduleRepository
    ){
        this.projectRepository = projectRepository;
        this.unitRepository = unitRepository;
        this.equityScheduleRepository = equityScheduleRepository;
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
            unitDto.setVersion(optUnit.get().getVersion());

            Set<ProjectContractEquitySchedule> equitySchedules = this.equityScheduleRepository.findByProjectContract(optUnit.get().getCurrentContract());
            unitDto.setEquitySchedules(equitySchedules);
        }

        return unitDto;

    }

    @Override
    @Transactional
    public ProjectUnitDto save(ProjectUnitDto unitDto) throws Exception {
        unitDto.setErrorCode(null);
        unitDto.setErrorDescription(null);

        ProjectUnit unit = null;

        if (unitDto.getUnitId() == null || unitDto.getUnitId() == 0) {
            unit = new ProjectUnit();

            unit.setUnitStatus(ProjectUnitStatus.AVAILABLE);

            Optional<Project> optProject = this.projectRepository.findById(unitDto.getProject().getProjectId());

            if (optProject.isPresent()) {
                unit.setProject(optProject.get());
            } else {
                unitDto.setErrorCode("1");
                unitDto.setErrorDescription("Project not found.");
                return unitDto;
            }
        } else {
            Optional<ProjectUnit> optUnit = this.unitRepository.findById(unitDto.getUnitId());

            if (optUnit.isPresent()) {
                unit = optUnit.get();
            } else {
                unitDto.setErrorCode("1");
                unitDto.setErrorDescription("Unit not found.");
                return unitDto;
            }

        }

        unit.setUnitCode(unitDto.getUnitCode());
        unit.setUnitDescription(unitDto.getUnitDescription());
        unit.setUnitPrice(unitDto.getUnitPrice());
        unit.setReservationAmt(unitDto.getReservationAmt());
        unit.setUnitClass(unitDto.getUnitClass());

        this.unitRepository.save(unit);

        unitDto.setUnitId(unit.getUnitId());
        unitDto.setUnitStatus(unit.getUnitStatus());
        unitDto.setProject(unit.getProject());

        return unitDto;
    }

}
