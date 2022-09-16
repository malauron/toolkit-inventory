package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "item_uoms")
public class ItemUom {

  @EmbeddedId
  private ItemUomId itemUomId;

//  @JsonBackReference
//  @ManyToOne
//  @JoinColumn(name = "item_id", insertable = false, updatable = false)
//  private Item item;
//
//  @ManyToOne
//  @JoinColumn(name = "uom_id", insertable = false, updatable = false)
//  private Uom uom;

  @Column(name = "quantity")
  private BigDecimal quantity;

}
