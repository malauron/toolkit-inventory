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
@Table(name = "butchery_livestock")
public class ButcheryLivestock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "butchery_livestock_id")
  private Long butcheryLivestockId;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "butchery_receiving_item_id")
  private ButcheryReceivingItem butcheryReceivingItem;

  @ManyToOne
  @JoinColumn(name = "vendor_id")
  private Vendor vendor;

  @Column(name = "livestock_type")
  @Enumerated(EnumType.STRING)
  private LivestockType livestockType;

  @Column(name = "no_of_heads")
  private BigDecimal noOfHeads;

  @Column(name = "weight")
  private BigDecimal  weight;

  @Column(name = "cost")
  private BigDecimal cost;

  @Column(name = "total_cost")
  private BigDecimal totalCost;

  @Version
  @Column(name = "version")
  private Long version;

}
