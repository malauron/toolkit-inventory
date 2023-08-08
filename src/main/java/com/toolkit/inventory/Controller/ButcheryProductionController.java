package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ButcheryProductionItem;
import com.toolkit.inventory.Domain.ButcheryProductionSource;
import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Dto.ButcheryProductionDto;
import com.toolkit.inventory.Dto.ButcheryReceivingDto;
import com.toolkit.inventory.Projection.ButcheryProductionAggregatedView;
import com.toolkit.inventory.Projection.ButcheryProductionItemAggregatedView;
import com.toolkit.inventory.Projection.ButcheryProductionSourceAggregatedView;
import com.toolkit.inventory.Projection.ButcheryProductionSourceShortView;
import com.toolkit.inventory.Service.ButcheryProductionService;
import com.toolkit.inventory.Service.ButcheryReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ButcheryProductionController {

  private ButcheryProductionService butcheryProductionService;
  private ButcheryReceivingService butcheryReceivingService;

  @Autowired
  public ButcheryProductionController(ButcheryProductionService butcheryProductionService,
                                      ButcheryReceivingService butcheryReceivingService) {
    this.butcheryProductionService = butcheryProductionService;
    this.butcheryReceivingService = butcheryReceivingService;
  }

  @GetMapping("/butcheryProductions")
  public ButcheryProductionDto getButcheryProduction(
          @RequestParam Long butcheryProductionId) {

    return this.butcheryProductionService.getButcheryProduction(butcheryProductionId);

  }

  @PostMapping("/butcheryProductions")
  public ButcheryProductionDto save(@RequestBody ButcheryProductionDto butcheryProductionDto) {

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

  @PutMapping("/butcheryProductionSources")
  public ButcheryProductionDto putButcheryProductionSource(@RequestBody ButcheryProductionSource butcheryProductionSource) {

    return this.butcheryProductionService.putButcheryProductionSource(butcheryProductionSource);

  }

  @DeleteMapping("/butcheryProductionSources")
  public ButcheryProductionDto deleteButcheryProductionSource(
          @RequestBody ButcheryProductionSource butcheryProductionSource) {

    return this.butcheryProductionService.deleteButcheryProductionSource(butcheryProductionSource);

  }

//  @GetMapping("/unitTest")
//  public Set<ButcheryProductionSourceAggregatedView> unitTest(@RequestParam Long id) {
//
//    return this.butcheryProductionService.unitTest(id);
//  }

  @GetMapping("/unitTest2")
  public Set<ButcheryProductionItemAggregatedView> unitTest2(@RequestParam Long id) {

    return this.butcheryProductionService.unitTest2(id);
  }

  @GetMapping("/unitTest3")
  public Set<ButcheryProductionAggregatedView> unitTest3() {

    return this.butcheryProductionService.unitTest3();
  }

//  @GetMapping("/butcheryProductionSources")
//  public ButcheryProductionDto findByButcheryReceivingId(@RequestParam Long butcheryReceivingId) {
//
//    return this.butcheryProductionService.findByButcheryReceivingId(butcheryReceivingId);
//  }
}
