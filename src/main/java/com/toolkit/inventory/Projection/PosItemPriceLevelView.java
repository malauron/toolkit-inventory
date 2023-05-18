package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.CustomerGroup;
import com.toolkit.inventory.Domain.PosItemPriceLevel;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "posItemPriceLevelView", types = {PosItemPriceLevel.class})
public interface PosItemPriceLevelView {
    Long getPosItemPriceLevelId();
    CustomerGroup getCustomerGroup();
    BigDecimal getPrice();
}
