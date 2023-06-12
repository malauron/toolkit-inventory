package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@IdClass(ItemUomId.class)
@Table(name = "item_uoms")
public class ItemUom {

  @Id
  @Column(name = "item_id")
  private Long itemId;

  @Id
  @Column(name = "uom_id")
  private Long uomId;

  @ManyToOne
  @JoinColumn(name = "item_id", insertable = false, updatable = false)
  private Item item;

  @Id
  @ManyToOne
  @JoinColumn(name = "uom_id", insertable = false, updatable = false)
  private Uom uom;


//  @EmbeddedId
//  private ItemUomId itemUomId;

//  @JsonBackReference
//  @ManyToOne
//  @JoinColumn(name = "item_id", insertable = false, updatable = false)
//  private Item item;

//  @ManyToOne
//  @JoinColumn(name = "uom_id", insertable = false, updatable = false)
//  private Uom uom;

  @Column(name = "quantity")
  private BigDecimal quantity;

}
