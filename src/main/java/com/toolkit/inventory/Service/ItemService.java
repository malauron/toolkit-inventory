package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemBom;
import com.toolkit.inventory.Domain.ItemGeneric;
import com.toolkit.inventory.Dto.ItemDto;

public interface ItemService {

    ItemDto save(ItemDto itemDto);

    ItemDto getItemBom(Long itemId);

    ItemBom addItemBom(ItemBom itemBom);

    void deleteItemBom(Long itemBomId);

    ItemDto getItemGeneric(Long itemId);

    ItemGeneric updateItemGeneric(ItemGeneric itemGeneric);

}
