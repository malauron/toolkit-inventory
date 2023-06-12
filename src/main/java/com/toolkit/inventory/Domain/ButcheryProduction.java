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
@Table(name = "butchery_productions")
public class ButcheryProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_production_id")
    private Long butcheryProductionId;


    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryProduction", cascade = CascadeType.ALL)
    private Set<ButcheryProductionItem> butcheryProductionItems =
            new HashSet<>();

    @JsonManagedReference(value = "butcheryProductionSource")
    @OneToMany(mappedBy = "butcheryProduction", cascade = CascadeType.ALL)
    private Set<ButcheryProductionSource> butcheryProductionSources =
            new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "production_status")
    private String productionStatus;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

    public void addButcheryProductionItem(ButcheryProductionItem butcheryProductionItem) {
        if (butcheryProductionItem != null) {
            if (this.butcheryProductionItems == null) {
                this.butcheryProductionItems = new HashSet<>();
            }

            this.butcheryProductionItems.add(butcheryProductionItem);
            butcheryProductionItem.setButcheryProduction(this);
        }
    }

    public void addButcheryProductionSource(ButcheryProductionSource butcheryProductionSource) {
        if (butcheryProductionSource != null) {
            if (this.butcheryProductionSources == null) {
                this.butcheryProductionSources = new HashSet<>();
            }

            this.butcheryProductionSources.add(butcheryProductionSource);
            butcheryProductionSource.setButcheryProduction(this);
        }
    }

}
