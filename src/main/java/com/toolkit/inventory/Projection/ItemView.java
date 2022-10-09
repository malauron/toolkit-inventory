package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "itemView", types = { Item.class })
public interface ItemView {

  Long getItemId();
  String getItemCode();
  String getItemName();
  Uom getUom();
  Date getDateCreated();
  String getItemClass();
  BigDecimal getPrice();
  Boolean getIsActive();

}