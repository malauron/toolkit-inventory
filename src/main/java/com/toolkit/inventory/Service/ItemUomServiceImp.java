package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Dto.ItemDto;
import com.toolkit.inventory.Repository.ItemRepository;
import com.toolkit.inventory.Repository.ItemUomRepository;
import com.toolkit.inventory.Repository.UomRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemUomServiceImp implements ItemUomService {

  private ItemRepository itemRepository;
  private UomRepository uomRepository;
  private ItemUomRepository itemUomRepository;

  public ItemUomServiceImp(ItemRepository itemRepository,
                           UomRepository uomRepository,
                           ItemUomRepository itemUomRepository) {
    this.itemRepository = itemRepository;
    this.uomRepository = uomRepository;
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

    ItemUom newItemUom = new ItemUom();

    newItemUom.setItemId(itemUom.getItem().getItemId());
    newItemUom.setUomId(itemUom.getUomId());
    newItemUom.setQuantity(itemUom.getQuantity());

    itemUomRepository.save(newItemUom);

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
