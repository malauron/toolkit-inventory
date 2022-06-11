package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "customer_charges")
public class CustomerCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_charge_id")
    private Long customerChargeId;

    @Column(name = "customer_allowable_charge_id")
    private Long customerAllowableChargeId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "charge_type_id")
    private Long chargeTypeId;

    @Column(name = "charge_amt")
    private BigDecimal chargeAmt;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

}
