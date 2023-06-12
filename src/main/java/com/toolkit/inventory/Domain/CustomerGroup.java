package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Entity
@Table(name = "customer_groups")
public class CustomerGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_group_id")
  private Long customerGroupId;

  @Column(name = "customer_group_name")
  private String customerGroupName;

}
