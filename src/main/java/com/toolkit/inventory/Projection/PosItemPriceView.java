package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PosItemPrice;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Set;

@Projection(name = "posItemPriceView", types = {PosItemPrice.class})
public interface PosItemPriceView {
    Long getPosItemPriceId();
    Set<PosItemPriceLevelView> getPosItemPriceLevels();
    Warehouse getWarehouse();
    Item getItem();
    BigDecimal getDefaultPrice();
}
