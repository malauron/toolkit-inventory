package com.toolkit.inventory.Domain;

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
@Table(name = "butchery_releasings")
public class ButcheryReleasing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_releasing_id")
    private Long butcheryReleasingId;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryReleasing", cascade = CascadeType.ALL)
    private Set<ButcheryReleasingItem> butcheryReleasingItems =
            new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouse;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "releasing_status")
    private String releasingStatus;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

    public void addButcheryReleasingItem(ButcheryReleasingItem butcheryReleasingItem) {
        if (butcheryReleasingItem != null) {
            if (this.butcheryReleasingItems == null) {
                this.butcheryReleasingItems = new HashSet<>();
            }

            this.butcheryReleasingItems.add(butcheryReleasingItem);
            butcheryReleasingItem.setButcheryReleasing(this);
        }
    }

}
