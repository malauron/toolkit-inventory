package com.toolkit.inventory.Domain;

import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "user_current_warehouse")
public class UserCurrentWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_current_warehouse_id")
    private Long userCurrentWarehouseId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
