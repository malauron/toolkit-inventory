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
@Table(name = "butchery_batches")
public class ButcheryBatchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_batch_detail_id")
    private Long butcheryBatchDetailId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_batch_id")
    private ButcheryBatch butcheryBatch;

    @Column(name = "reference")
    private String reference;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryBatchDetail", cascade = CascadeType.ALL)
    private Set<ButcheryBatchDetailItem> butcheryBatchDetailItems =
            new HashSet<>();

}
