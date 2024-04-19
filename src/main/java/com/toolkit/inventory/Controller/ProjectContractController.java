package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.ProjectContractDto;
import com.toolkit.inventory.Service.ProjectContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProjectContractController {

    final ProjectContractService contractService;

    @Autowired
    public ProjectContractController(ProjectContractService contractService) {
        this.contractService = contractService;

    }

    @GetMapping("/projectContracts")
    public ProjectContractDto save(@RequestBody ProjectContractDto contractDto) {

        try {
            return this.contractService.save(contractDto);
        } catch (Exception e) {
            contractDto.setErrorCode(e.getClass().getName());
            contractDto.setErrorDescription(e.getMessage());
            return contractDto;
        }
    }
}
