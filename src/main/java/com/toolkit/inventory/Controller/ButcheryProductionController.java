package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ButcheryProduction;
import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Dto.ButcheryProductionDto;
import com.toolkit.inventory.Service.ButcheryProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ButcheryProductionController {

  private ButcheryProductionService butcheryProductionService;

  @Autowired
  public ButcheryProductionController(ButcheryProductionService butcheryProductionService) {
    this.butcheryProductionService = butcheryProductionService;
  }

  @GetMapping("butcheryProductions")
  public ButcheryProductionDto getButcheryProduction(
          @RequestParam Long butcheryProductionId) {

    return this.butcheryProductionService.getButcheryProduction(butcheryProductionId);

  }

  @PostMapping("/butcheryProductions")
  public ButcheryProduction save(@RequestBody ButcheryProductionDto butcheryProductionDto) {

    return this.butcheryProductionService.save(butcheryProductionDto);

  }

  @PutMapping("/butcheryProductions")
  public ButcheryProductionDto setButcheryProduction(@RequestBody ButcheryProductionDto butcheryProductionDto) {

    return this.butcheryProductionService.setButcheryProduction(butcheryProductionDto);

  }

  @PutMapping("/butcheryProductions/productionStatus")
  public ButcheryProductionDto setProductionStatus(@RequestBody ButcheryProductionDto butcheryProductionDto) {

    return this.butcheryProductionService.setProductionStatus(butcheryProductionDto);

  }

  @PutMapping("/butcheryProductionItems")
  public ButcheryProductionDto putButcheryProductionItem(@RequestBody ButcheryProductionItem butcheryProductionItem) {

    return this.butcheryProductionService.putButcheryProductionItem(butcheryProductionItem);

  }

  @DeleteMapping("/butcheryProductionItems")
  public ButcheryProductionDto deleteButcheryProductionItem(
          @RequestBody ButcheryProductionItem butcheryProductionItem) {

    return this.butcheryProductionService.deleteButcheryProductionItem(butcheryProductionItem);

  }


  
}
