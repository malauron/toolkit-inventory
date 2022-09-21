package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.UomRepository;
import com.toolkit.inventory.Service.ItemUomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ItemUomController {

  private ItemUomService itemUomService;

  public ItemUomController(ItemUomService itemUomService) {
    this.itemUomService = itemUomService;
  }

  @PostMapping("/itemUoms")
  public ItemUom save(@RequestBody ItemUom itemUomParam) {
    itemUomService.save(itemUomParam);
    return itemUomParam;
  }

  @PutMapping("/itemUoms")
  public ItemUom update(@RequestBody ItemUom itemUomParam) {

    itemUomService.save(itemUomParam);
    return itemUomParam;
  }

  @PutMapping("/itemUoms-QueryUpdate")
  public ItemUom updateQuery(@RequestBody ItemUom itemUomParam) {

    itemUomService.update(itemUomParam);
    return itemUomParam;
  }

  @DeleteMapping("/itemUoms")
  public ItemUom delete(@RequestBody ItemUom itemUomParam) {

    itemUomService.delete(itemUomParam);
    return itemUomParam;
  }


}