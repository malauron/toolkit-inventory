package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Service.ButcheryReleasingService;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

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

  @GetMapping("/butcheryReleasingItems")
  public Set<ButcheryReleasingItem> getButcheryReleasingItems(@RequestParam Long butcheryReleasingId) {

    return this.butcheryReleasingService.getButcheryReleasingItems(butcheryReleasingId);

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

    try {

      return this.butcheryReleasingService.setReleasingStatus(butcheryReleasingDto);

    } catch (NoSuchElementException ex) {
      butcheryReleasingDto.setErrorDescription("No Such Element Exception");
      return butcheryReleasingDto;
    } catch (StaleStateException ex) {
      butcheryReleasingDto.setErrorDescription("Stale State Exception");
      return butcheryReleasingDto;
    } catch (ObjectOptimisticLockingFailureException ex){
      butcheryReleasingDto.setErrorDescription("Stale data detected. Please try again.");
      return butcheryReleasingDto;
    } catch (Exception ex) {
      butcheryReleasingDto.setErrorDescription(ex.getMessage());
      return butcheryReleasingDto;
    }

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
