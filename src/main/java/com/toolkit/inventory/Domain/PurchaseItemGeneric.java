package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Entity
@Table(name = "purchase_item_generics")
public class PurchaseItemGeneric {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "purchase_item_generic_id")
  private Long purchaseItemGenericId;

  @OneToOne
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

  @Column(name = "required_qty")
  private BigDecimal requiredQty;

  @Column(name = "purchased_qty")
  private BigDecimal purchasedQty;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "total_amount")
  private BigDecimal totalAmount;

}
