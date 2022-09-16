package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Domain.Uom;
import com.toolkit.inventory.Dto.ItemUomIdDto;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.UomRepository;
import com.toolkit.inventory.Service.ItemUomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ItemUomController {

  private ItemUomService itemUomService;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private UomRepository uomRepository;

  public ItemUomController(ItemUomService itemUomService) {
    this.itemUomService = itemUomService;
  }

  @GetMapping("/itemUoms")
  public ItemUomIdDto getItemUoms(@RequestBody ItemUomIdDto itemUomIddto) {

    ItemUomId itemUomId = new ItemUomId();

    Optional<Item> tmpItem = this.itemRepository.findById(itemUomIddto.getItemId());
    if (tmpItem.isPresent()) {
      itemUomId.setItem(tmpItem.get());
    }

    Optional<Uom> tmpUom = this.uomRepository.findById(itemUomIddto.getUomId());
    if (tmpUom.isPresent()) {
      itemUomId.setUom(tmpUom.get());
    }

    System.out.println(itemUomId.getItem());
    System.out.println(itemUomId.getUom());

    ItemUomIdDto itemUomIdDto = new ItemUomIdDto();

//    itemUomIdDto.setItemUoms(this.itemUomService.getItemUoms(itemUomId));
    this.itemUomService.getItemUoms(itemUomId);

//    return itemUomIdDto;
    return null;
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