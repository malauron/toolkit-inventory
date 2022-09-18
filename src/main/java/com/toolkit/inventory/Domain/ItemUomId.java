package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ItemUomId implements Serializable {

  private Long itemId;

  private Long uomId;

}
