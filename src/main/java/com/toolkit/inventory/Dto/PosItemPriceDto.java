package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PosItemPriceLevel;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class PosItemPriceDto {

    private Long posItemPriceId;
    private Set<PosItemPriceLevel> posItemPriceLevels;
    private Warehouse warehouse;
    private Item item;
    private BigDecimal defaultPrice;

}
