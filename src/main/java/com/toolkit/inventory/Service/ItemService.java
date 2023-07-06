package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ItemDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

public interface ItemService {

    ItemDto findByItemCode(String itemCode);

    ItemDto save(ItemDto itemDto) throws DataIntegrityViolationException, Exception;

    ItemDto getItemBom(Long itemId);

    ItemDto getItemAddOns(Long itemId);

    ItemBom addItemBom(ItemBom itemBom);

    ItemAddOnDetail addItemAddOnDetail(ItemAddOnDetail itemAddOnDetail);

    ItemAddOnContent addItemAddOnContent(ItemAddOnContent itemAddOnContent);

    void deleteItemBom(Long itemBomId);

    void deleteItemAddOnDetail(Long itemAddOnDetailId);

    void deleteItemAddOnContent(Long itemAddOnContentId);

    ItemDto getItemGeneric(Long itemId);

    ItemGeneric updateItemGeneric(ItemGeneric itemGeneric);

    Set<ItemCost> getItemCosts(Long warehouseId);

}
