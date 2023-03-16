package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.CustomerGroup;
import com.toolkit.inventory.Domain.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPriceDto {

    private Long itemPriceId;
    private Item item;
    private CustomerGroup customerGroup;
    private BigDecimal price;

}
