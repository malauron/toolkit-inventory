package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryItemDto {

    private Long inventoryItemId;
    private Item item;
    private Warehouse warehouse;
    private BigDecimal beginningQty;
    private BigDecimal purchasedQty;
    private BigDecimal endingQty;
    private BigDecimal cost;
    private BigDecimal price;
    private BigDecimal qty;

}
