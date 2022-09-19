package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Dto.ItemDto;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.ItemUomRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemUomServiceImp implements ItemUomService {

  private ItemRepository itemRepository;
  private ItemUomRepository itemUomRepository;

  public ItemUomServiceImp(ItemRepository itemRepository,
                           ItemUomRepository itemUomRepository) {
    this.itemRepository = itemRepository;
    this.itemUomRepository = itemUomRepository;
  }

  @Override
  public ItemDto getItemUoms(Long itemId) {

    Optional<Item> optItem = this.itemRepository.findById(itemId);
    Set<ItemUom> itemUoms = this.itemUomRepository.findByItem(optItem.get());

    ItemDto itemDto = new ItemDto();
    itemDto.setItemUoms(itemUoms);
    return itemDto;

  }

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
//    itemUomRepository.setQuantity(itemUom.getItemUomId(), itemUom.getQuantity());
  }

  @Override
  public void delete(ItemUom itemUom) {
    itemUomRepository.delete(itemUom);
  }

}
