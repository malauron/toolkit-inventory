package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name =  "total_price")
  private BigDecimal totalPrice;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  @Column(name = "order_status")
  private String orderStatus;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  Set<OrderMenu> orderMenus = new HashSet<>();

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;

  @UpdateTimestamp
  @Column(name = "date_updated")
  private Date dateUpdated;

  @Version
  @Column(name = "version")
  private Long version;

  public void addMenu(OrderMenu menu) {
    if (menu != null) {
      if (orderMenus == null) {
        orderMenus = new HashSet<>();
      }

      orderMenus.add(menu);
      menu.setOrder(this);
    }
  }

}
