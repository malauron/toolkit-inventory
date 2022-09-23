package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemBom;
import com.toolkit.inventory.Domain.ItemGeneric;
import com.toolkit.inventory.Domain.ItemUom;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ItemDto {
    private Item item;
    private Set<ItemUom> itemUoms;
    private Set<ItemBom> itemBoms;
    private ItemGeneric itemGeneric;
}
