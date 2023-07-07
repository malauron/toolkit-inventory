package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.ItemAddOnContent;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "itemAddOnContentView", types = {ItemAddOnContent.class})
public interface ItemAddOnContentView {

    Long getItemAddOnContentId();
    Item getItem();
    Uom getUom();
    BigDecimal getPrice();
    String getAltDesc();

}
