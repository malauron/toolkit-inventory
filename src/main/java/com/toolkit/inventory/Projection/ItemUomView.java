package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ItemUom;
import com.toolkit.inventory.Domain.ItemUomId;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "itemUomView", types = {ItemUom.class})
public interface ItemUomView {

//  @Value("#{target.itemUomId.getItemId()}")
//  Long getItemId();
//  @Value("#{target.itemUomId.getUomId()}")
//  Long getUomId();
  ItemUomId getItemUomId();
  Uom getUom();
  BigDecimal getQuantity();

}
