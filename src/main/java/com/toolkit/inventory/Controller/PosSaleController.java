package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.PosSale;
import com.toolkit.inventory.Domain.PosSaleItem;
import com.toolkit.inventory.Dto.PosSaleDto;
import com.toolkit.inventory.Service.PosSaleService;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PosSaleController {

  private PosSaleService posSaleService;

  @Autowired
  public PosSaleController(PosSaleService posSaleService) {
    this.posSaleService = posSaleService;
  }

  @GetMapping("/posSales")
  public PosSaleDto getPosSale(
          @RequestParam Long posSaleId) {

    return this.posSaleService.getPosSale(posSaleId);

  }

  @GetMapping("/posSaleItems")
  public Set<PosSaleItem> getPosSaleItems(@RequestParam Long posSaleId) {

    return this.posSaleService.getPosSaleItems(posSaleId);

  }

  @PostMapping("/posSales")
  public PosSale save(@RequestBody PosSaleDto posSaleDto) {

    return this.posSaleService.save(posSaleDto);

  }

  @PutMapping("/posSales")
  public PosSaleDto setPosSale(@RequestBody PosSaleDto posSaleDto) {

    return this.posSaleService.setPosSale(posSaleDto);

  }

  @PutMapping("/posSales/saleStatus")
  public PosSaleDto setSaleStatus(@RequestBody PosSaleDto posSaleDto) {

    try {

      return this.posSaleService.setSaleStatus(posSaleDto);

    } catch (NoSuchElementException ex) {
      posSaleDto.setErrorDescription("No Such Element Exception");
      return posSaleDto;
    } catch (StaleStateException ex) {
      posSaleDto.setErrorDescription("Stale State Exception");
      return posSaleDto;
    } catch (ObjectOptimisticLockingFailureException ex){
      posSaleDto.setErrorDescription("Stale data detected. Please try again.");
      return posSaleDto;
    } catch (Exception ex) {
      posSaleDto.setErrorDescription(ex.getMessage());
      return posSaleDto;
    }

  }

  @PutMapping("/posSaleItems")
  public PosSaleDto putPosSaleItem(@RequestBody PosSaleItem posSaleItem) {

    return this.posSaleService.putPosSaleItem(posSaleItem);

  }

  @DeleteMapping("/posSaleItems")
  public PosSaleDto deletePosSaleItem(
          @RequestBody PosSaleItem posSaleItem) {

    return this.posSaleService.deletePosSaleItem(posSaleItem);

  }


  
}
