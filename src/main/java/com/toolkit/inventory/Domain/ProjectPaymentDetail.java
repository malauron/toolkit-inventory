package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "project_payment_details")
public class ProjectPaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_detail_id")
    private Long paymentDetailId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private ProjectPayment projectPayment;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ProjectContract contract;

    @Column(name = "reservation")
    private BigDecimal reservation;

    @Column(name = "equity")
    private BigDecimal equity;

    @Column(name = "financing")
    private BigDecimal financing;

    @Column(name = "others")
    private BigDecimal others;

    @Column(name = "ttl_amt_paid")
    private BigDecimal ttlAmtPaid;
}
