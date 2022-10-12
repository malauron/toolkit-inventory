package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Service.ButcheryReleasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ButcheryReleasingController {

  private ButcheryReleasingService butcheryReleasingService;

  @Autowired
  public ButcheryReleasingController(ButcheryReleasingService butcheryReleasingService) {
    this.butcheryReleasingService = butcheryReleasingService;
  }

  @GetMapping("/butcheryReleasings")
  public ButcheryReleasingDto getButcheryReleasing(
          @RequestParam Long butcheryReleasingId) {

    return this.butcheryReleasingService.getButcheryReleasing(butcheryReleasingId);

  }

  @PostMapping("/butcheryReleasings")
  public ButcheryReleasing save(@RequestBody ButcheryReleasingDto butcheryReleasingDto) {

    return this.butcheryReleasingService.save(butcheryReleasingDto);

  }

  @PutMapping("/butcheryReleasings")
  public ButcheryReleasingDto setButcheryReleasing(@RequestBody ButcheryReleasingDto butcheryReleasingDto) {

    return this.butcheryReleasingService.setButcheryReleasing(butcheryReleasingDto);

  }

  @PutMapping("/butcheryReleasings/releasingStatus")
  public ButcheryReleasingDto setReleasingStatus(@RequestBody ButcheryReleasingDto butcheryReleasingDto) {

    return this.butcheryReleasingService.setReleasingStatus(butcheryReleasingDto);

  }

  @PutMapping("/butcheryReleasingItems")
  public ButcheryReleasingDto putButcheryReleasingItem(@RequestBody ButcheryReleasingItem butcheryReleasingItem) {

    return this.butcheryReleasingService.putButcheryReleasingItem(butcheryReleasingItem);

  }

  @DeleteMapping("/butcheryReleasingItems")
  public ButcheryReleasingDto deleteButcheryReleasingItem(
          @RequestBody ButcheryReleasingItem butcheryReleasingItem) {

    return this.butcheryReleasingService.deleteButcheryReleasingItem(butcheryReleasingItem);

  }


  
}
