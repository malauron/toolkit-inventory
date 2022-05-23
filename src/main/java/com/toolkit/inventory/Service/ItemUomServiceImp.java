package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Repository.ItemUomRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemUomServiceImp implements ItemUomService {

  private ItemUomRepository itemUomRepository;

  public ItemUomServiceImp(ItemUomRepository itemUomRepository) {this.itemUomRepository = itemUomRepository;}

  @Override
  public void save(ItemUom itemUom) {
    itemUomRepository.save(itemUom);
  }

  @Override
  public void update(ItemUom itemUom) {
    itemUomRepository.save(itemUom);
  }

  @Override
  @Transactional
  public void updateQuery(ItemUom itemUom) {
    itemUomRepository.setQuantity(itemUom.getItemUomId(), itemUom.getQuantity());
  }

  @Override
  public void delete(ItemUom itemUom) {
    itemUomRepository.delete(itemUom);
  }


}
