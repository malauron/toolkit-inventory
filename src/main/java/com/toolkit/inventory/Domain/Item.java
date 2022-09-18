package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long itemId;

  @Column(name = "item_name", unique = true)
  private String itemName;

  @ManyToOne
  @JoinColumn(name = "uom_id")
  private Uom uom;

  @Column(name = "item_class")
  private String itemClass;

  @Column(name = "is_active")
  private Boolean isActive;

//  @JsonManagedReference
//  @OneToMany(mappedBy = "itemUomId.item", cascade = CascadeType.ALL)
//  private Set<ItemUom> itemUom;

//  @JsonManagedReference
//  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//  private Set<ItemCost> itemCosts;

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;

  @UpdateTimestamp
  @Column(name = "date_updated")
  private Date dateUpdated;

//  public void addItemCost(ItemCost itemCost) {
//    if (itemCost != null) {
//      if (itemCosts == null) {
//        itemCosts = new HashSet<>();
//      }
//
//      itemCosts.add(itemCost);
//      itemCost.setItem(this);
//    }
//  }

}
