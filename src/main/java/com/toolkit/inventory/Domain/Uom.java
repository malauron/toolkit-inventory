package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "uoms")
public class Uom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "uom_id")
  private Long uomId;

  @Column(name = "uom_code")
  private String uomCode;

  @Column(name = "uom_name")
  private String uomName;

}
