package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "purchase_item_boms")
public class PurchaseItemBom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "purchase_item_bom_id")
  private Long purchaseItemBomId;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "purchase_item_id")
  private PurchaseItem purchaseItem;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "base_uom_id")
  private Uom baseUom;

  @Column(name = "base_qty")
  private BigDecimal baseQty;

  @ManyToOne
  @JoinColumn(name = "required_uom_id")
  private Uom requiredUom;

  @Column(name = "purchased_qty")
  private BigDecimal purchasedQty;

  @Column(name = "cost")
  private BigDecimal cost;

}
