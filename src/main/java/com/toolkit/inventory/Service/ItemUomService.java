package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Dto.ItemDto;

public interface ItemUomService {

  ItemDto getItemUoms(Long itemId);
  void save(ItemUom itemUom);
  void updateQuery(ItemUom itemUom);
  void delete(ItemUom itemUom);
  void update(ItemUom itemUom);
}
