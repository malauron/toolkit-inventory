package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.InventoryHistoryItem;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class InventoryHistoryDto {

    private Long inventoryHistoryId;
    private Warehouse warehouse;
    private Set<InventoryHistoryItem> inventoryHistoryItems;
}
