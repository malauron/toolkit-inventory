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

@Entity
@Getter
@Setter
@Table(name = "butchery_Receivings")
public class ButcheryReceiving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_receiving_id")
    private Long butcheryReceivingId;

    @JsonManagedReference
    @OneToMany(mappedBy = "butcheryReceiving", cascade = CascadeType.ALL)
    private Set<ButcheryReceivingItem> butcheryReceivingItems =
            new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "receiving_status")
    private String receivingStatus;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

    public void addButcheryReceivingItem(ButcheryReceivingItem butcheryReceivingItem) {
        if (butcheryReceivingItem != null) {
            if (this.butcheryReceivingItems == null) {
                this.butcheryReceivingItems = new HashSet<>();
            }

            this.butcheryReceivingItems.add(butcheryReceivingItem);
            butcheryReceivingItem.setButcheryReceiving(this);
        }
    }

}
