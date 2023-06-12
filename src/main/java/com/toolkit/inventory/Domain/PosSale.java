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
@Table(name = "pos_sales")
public class PosSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_sale_id")
    private Long posSaleId;

    @JsonManagedReference
    @OneToMany(mappedBy = "posSale", cascade = CascadeType.ALL)
    private Set<PosSaleItem> posSaleItems =
            new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "sale_status")
    private String saleStatus;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

    @Version
    @Column(name = "version")
    private Long version;

    public void addPosSaleItem(PosSaleItem posSaleItem) {
        if (posSaleItem != null) {
            if (this.posSaleItems == null) {
                this.posSaleItems = new HashSet<>();
            }

            this.posSaleItems.add(posSaleItem);
            posSaleItem.setPosSale(this);
        }
    }

}
