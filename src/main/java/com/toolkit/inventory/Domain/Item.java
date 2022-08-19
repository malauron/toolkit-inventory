package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="item_id")
  private Long itemId;

  @Column(name="item_name")
  private String itemName;

  @ManyToOne
  @JoinColumn(name = "uom_id")
  private Uom uom;

  @JsonManagedReference
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  private Set<ItemUom> itemUom;

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;

  @UpdateTimestamp
  @Column(name = "date_updated")
  private Date dateUpdated;

}
