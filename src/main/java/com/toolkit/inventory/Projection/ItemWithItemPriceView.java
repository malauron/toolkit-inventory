package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.PosItemPrice;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Set;

@Projection(name = "itemWithItemPriceView", types = {Item.class})
public interface ItemWithItemPriceView {

    Long getItemId();
    String getItemCode();
    String getItemName();
    Uom getUom();
    BigDecimal getPrice();
//    @Value("#{@posItemPriceRepository.findByItem(target)}")
//    Set<PosItemPrice> getPosItemPrices();

}
