package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryBatchDetail", cascade = CascadeType.ALL)
    private Set<ButcheryBatchDetailItem> butcheryBatchDetailItems =
            new HashSet<>();

}
