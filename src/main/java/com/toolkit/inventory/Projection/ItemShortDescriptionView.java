package com.toolkit.inventory.Projection;


import com.toolkit.inventory.Domain.Item;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "itemShortDescriptionView", types = { Item.class })
public interface ItemShortDescriptionView {
  Long getItemId();
  String getItemName();
}
