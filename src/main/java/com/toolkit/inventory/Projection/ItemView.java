package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Item;
import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "itemView", types = { Item.class })
public interface ItemView {

  Long getItemId();
  String getItemName();
  Uom getUom();
  Date getDateCreated();
//  Set<ItemUom> getItemUom();
}