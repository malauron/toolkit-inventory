package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.Uom;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "uomView", types = { Uom.class })
public interface UomView {
  Long getUomId();
  String getUomCode();
  String getUomName();
}
