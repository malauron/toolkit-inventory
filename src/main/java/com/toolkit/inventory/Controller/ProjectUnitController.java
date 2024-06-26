package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.ProjectUnitDto;
import com.toolkit.inventory.Service.ProjectUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProjectUnitController {

    private ProjectUnitService unitService;

    @Autowired
    public ProjectUnitController(ProjectUnitService unitService){
        this.unitService = unitService;
    }

    @GetMapping("/projectUnits")
    public ProjectUnitDto getProjectUnit(@RequestParam Long unitId) {
        return this.unitService.getUnit(unitId);
    }

    @PostMapping("/projectUnits")
    public ProjectUnitDto save(@RequestBody ProjectUnitDto projectUnitDto)  {

        try {
            return this.unitService.save(projectUnitDto);
        } catch (Exception e) {
            projectUnitDto.setErrorCode(e.getClass().getName());
            projectUnitDto.setErrorDescription(e.getMessage());
            return projectUnitDto;
        }
    }
}
