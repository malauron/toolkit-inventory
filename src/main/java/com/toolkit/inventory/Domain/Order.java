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

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name =  "total_price")
  private BigDecimal totalPrice;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  Set<OrderMenu> orderMenus = new HashSet<>();

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;

  @UpdateTimestamp
  @Column(name = "date_updated")
  private Date dateUpdated;

}
