package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ItemBom;
import com.toolkit.inventory.Domain.ItemCost;
import com.toolkit.inventory.Domain.ItemGeneric;
import com.toolkit.inventory.Dto.ItemDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

public interface ItemService {

    ItemDto findByItemCode(String itemCode);

    ItemDto save(ItemDto itemDto) throws DataIntegrityViolationException, Exception;

    ItemDto getItemBom(Long itemId);

    ItemBom addItemBom(ItemBom itemBom);

    void deleteItemBom(Long itemBomId);

    ItemDto getItemGeneric(Long itemId);

    ItemGeneric updateItemGeneric(ItemGeneric itemGeneric);

    Set<ItemCost> getItemCosts(Long warehouseId);

}
