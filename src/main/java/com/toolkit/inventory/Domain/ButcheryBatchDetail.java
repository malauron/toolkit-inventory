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
@Table(name = "butchery_batch_details")
public class ButcheryBatchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_batch_detail_id")
    private Long butcheryBatchDetailId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_batch_id")
    private ButcheryBatch butcheryBatch;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "total_documented_weight_kg")
    private BigDecimal totalDocumentedWeightKg;

    @Column(name = "total_received_weight_kg")
    private BigDecimal totalReceivedWeightKg;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryBatchDetail", cascade = CascadeType.ALL)
    private Set<ButcheryBatchDetailItem> butcheryBatchDetailItems =
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
