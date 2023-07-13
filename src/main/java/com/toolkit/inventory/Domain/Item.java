package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long itemId;

  @Column(name = "item_code")
  private String itemCode;

  @Column(name = "item_name", unique = true)
  private String itemName;

  @ManyToOne
  @JoinColumn(name = "uom_id")
  private Uom uom;

  @Column(name = "item_class")
  @Enumerated(EnumType.STRING)
  private ItemClass itemClass;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_weighable")
  private Boolean isWeighable;

  @Column(name = "is_weight_based_cost")
  private Boolean isWeightBasedCost;

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;

  @UpdateTimestamp
  @Column(name = "date_updated")
  private Date dateUpdated;

}
