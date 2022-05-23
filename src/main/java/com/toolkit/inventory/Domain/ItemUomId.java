package com.toolkit.inventory.Domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
//
//@Getter
//@Setter
@Embeddable
public class ItemUomId implements Serializable {

  @Column(name = "item_id")
  private Long itemId;


  @Column(name = "uom_id")
  private Long uomId;

}
