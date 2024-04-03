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
@Table(name = "project_units")
public class ProjectUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_code")
    private String unitCode;

    @Column(name = "unit_description")
    private String unitDescription;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "reservation_amt")
    private BigDecimal reservationAmt;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @Column(name = "unit_class")
    @Enumerated(EnumType.STRING)
    private ProjectUnitClass unitClass;

    @Column(name = "unit_status")
    @Enumerated(EnumType.STRING)
    private ProjectUnitStatus unitStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_contract_id", referencedColumnName = "contract_id")
    private ProjectContract currentContract;

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
