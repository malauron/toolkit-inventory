package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Menu;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "menuView", types = { Menu.class })
public interface MenuView {
  Long getMenuId();
  String getMenuName();
  BigDecimal getPrice();
  String getRemarks();
  String getAltRemarks();
  Date getDateCreated();
  Date getDateUpdated();
}
