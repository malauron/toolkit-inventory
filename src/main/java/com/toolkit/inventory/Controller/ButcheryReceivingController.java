package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Dto.ButcheryReceivingDto;
import com.toolkit.inventory.Service.ButcheryReceivingService;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ButcheryReceivingController {

  private ButcheryReceivingService butcheryReceivingService;

  @Autowired
  public ButcheryReceivingController(ButcheryReceivingService butcheryReceivingService) {
    this.butcheryReceivingService = butcheryReceivingService;
  }

  @GetMapping("/butcheryReceivings")
  public ButcheryReceivingDto getButcheryReceiving(
          @RequestParam Long butcheryReceivingId) {

    return this.butcheryReceivingService.getButcheryReceiving(butcheryReceivingId);

  }

  @PostMapping("/butcheryReceivings")
  public ButcheryReceiving save(@RequestBody ButcheryReceivingDto butcheryReceivingDto) {

    return this.butcheryReceivingService.save(butcheryReceivingDto);

  }

  @PutMapping("/butcheryReceivings")
  public ButcheryReceivingDto setButcheryReceiving(@RequestBody ButcheryReceivingDto butcheryReceivingDto) {

    return this.butcheryReceivingService.setButcheryReceiving(butcheryReceivingDto);

  }

  @PutMapping("/butcheryReceivings/receivingStatus")
  public ButcheryReceivingDto setReceivingStatus(@RequestBody ButcheryReceivingDto butcheryReceivingDto) {

    try {

      return this.butcheryReceivingService.setReceivingStatus(butcheryReceivingDto);

    } catch (NoSuchElementException ex) {
      butcheryReceivingDto.setErrorDescription("No Such Element Exception");
      return butcheryReceivingDto;
    } catch (StaleStateException ex) {
      butcheryReceivingDto.setErrorDescription("Stale State Exception");
      return butcheryReceivingDto;
    } catch (ObjectOptimisticLockingFailureException ex){
      butcheryReceivingDto.setErrorDescription("Stale data detected. Please try again.");
      return butcheryReceivingDto;
    } catch (Exception ex) {
      butcheryReceivingDto.setErrorDescription(ex.getMessage());
      return butcheryReceivingDto;
    }

  }

  @PutMapping("/butcheryReceivingItems")
  public ButcheryReceivingDto putButcheryReceivingItem(@RequestBody ButcheryReceivingItem butcheryReceivingItem) {

    return this.butcheryReceivingService.putButcheryReceivingItem(butcheryReceivingItem);

  }

  @DeleteMapping("/butcheryReceivingItems")
  public ButcheryReceivingDto deleteButcheryReceivingItem(
          @RequestBody ButcheryReceivingItem butcheryReceivingItem) {

    return this.butcheryReceivingService.deleteButcheryReceivingItem(butcheryReceivingItem);

  }


  
}
