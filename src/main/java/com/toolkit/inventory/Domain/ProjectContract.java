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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "project_contracts")
public class ProjectContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ProjectClient client;

    @ManyToOne
    @JoinColumn(name = "brokerage_id")
    private ProjectBrokerage brokerage;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private ProjectBroker broker;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private ProjectUnit unit;

    @Column(name = "reservation_amt")
    private BigDecimal reservationAmt;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "equity_amt")
    private BigDecimal equityAmt;

    @Column(name = "equity_pct")
    private BigDecimal equityPct;

    @Column(name = "ttl_equity_paid")
    private BigDecimal ttlEquityPaid;

    @Column(name = "equity_balance")
    private BigDecimal equityBalance;

    @Column(name = "financing_amt")
    private BigDecimal financingAmt;

    @Column(name = "financing_pct")
    private BigDecimal financingPct;

    @Column(name = "ttl_financing_paid")
    private BigDecimal ttlFinancingPaid;

    @Column(name = "financing_balance")
    private BigDecimal financingBalance;

    @Column(name = "ttl_payment")
    private BigDecimal ttlPayment;

    @Column(name = "ttl_balance")
    private BigDecimal ttlBalance;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_cancelled")
    private String isCancelled;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

}
