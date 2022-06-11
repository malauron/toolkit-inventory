package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "customerAllowableChargeId")
@Table(name = "customer_allowable_charges")
public class CustomerAllowableCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_allowable_charge_id")
    private Long customerAllowableChargeId;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "charge_type_id")
    private Long chargeTypeId;

    @Column(name = "limit_amt")
    private BigDecimal limitAmt;

    @Column(name = "total_purchase")
    private BigDecimal totalPurchase;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

}
