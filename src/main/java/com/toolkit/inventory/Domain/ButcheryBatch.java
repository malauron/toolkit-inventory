package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toolkit.inventory.Security.Domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "butchery_batches")
public class ButcheryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_batch_id")
    private Long butcheryBatchId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "date_received")
    private Date dateReceived;

    @Column(name = "has_inventory")
    private Boolean hasInventory;

    @Column(name = "is_open")
    private Boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryBatch", cascade = CascadeType.ALL)
    private Set<ButcheryBatchDetail> butcheryBatchDetails =
            new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryBatch", cascade = CascadeType.ALL)
    private Set<ButcheryBatchInventory> butcheryBatchInventories =
            new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

}
