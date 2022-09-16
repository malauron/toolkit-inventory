package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;

import java.util.Set;

public interface ItemUomService {

  Set<ItemUom> getItemUoms(ItemUomId itemUomId);
  void save(ItemUom itemUom);
  void updateQuery(ItemUom itemUom);
  void delete(ItemUom itemUom);
  void update(ItemUom itemUom);
}
