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
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "total_amt")
    private BigDecimal totalAmt;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "purchase_status")
    private String purchaseStatus;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    Set<PurchaseItem> purchaseItems = new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

    public void addPurchaseItem(PurchaseItem purchaseItem) {
        if (purchaseItem != null) {
            if (purchaseItems == null) {
                purchaseItems = new HashSet<>();
            }

            purchaseItems.add(purchaseItem);
            purchaseItem.setPurchase(this);
        }
    }
}
