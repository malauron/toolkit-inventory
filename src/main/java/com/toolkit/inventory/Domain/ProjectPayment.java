package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toolkit.inventory.Security.Domain.User;
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
@Table(name = "project_payments")
public class ProjectPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ProjectClient client;

    @Column(name = "ttl_amt_paid")
    private BigDecimal ttlAmtPaid;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "date_received")
    private Date dateReceived;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "projectPayment", cascade = CascadeType.ALL)
    private Set<ProjectPaymentDetail> projectPaymentDetails =
            new HashSet<>();

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
