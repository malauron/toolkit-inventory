package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "butchery_batch_detail_items")
public class ButcheryBatchDetailItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_batch_detail_item_id")
    private Long butcheryBatchDetailItemId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_batch_detail_id")
    private ButcheryBatchDetail butcheryBatchDetail;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "base_uom_id")
    private Uom baseUom;

    @Column(name = "base_qty")
    private BigDecimal baseQty;

    @ManyToOne
    @JoinColumn(name = "required_uom_id")
    private Uom requiredUom;

    @Column(name = "required_qty")
    private BigDecimal requiredQty;

    @Column(name = "received_qty")
    private BigDecimal receivedQty;

    @Column(name = "required_weight_kg")
    private BigDecimal requiredWeightKg;

    @Column(name = "received_weight_kg")
    private BigDecimal receivedWeightKg;

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
