package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.*;
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
    private String errorDesc;
    private Set<ItemAddOnDetail> itemAddOnDetails;
}
