package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "customer_pictures")
public class CustomerPicture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_picture_id")
  private Long customerPictureId;

  @JsonBackReference
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
  private Customer customer;

  @Lob
  @Column(name = "file")
  private byte[] file;

  @Column(name = "type")
  private String type;

}
