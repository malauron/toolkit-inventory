package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;

public interface ItemUomService {

  void save(ItemUom itemUom);
  void updateQuery(ItemUom itemUom);
  void delete(ItemUom itemUom);
  void update(ItemUom itemUom);
}
