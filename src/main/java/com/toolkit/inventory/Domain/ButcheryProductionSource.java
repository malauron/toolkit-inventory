package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "butchery_production_sources")
public class ButcheryProductionSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "butchery_production_source_id")
    private Long butcheryProductionSourceId;

    @JsonBackReference(value="butcheryProductionSource")
    @ManyToOne
    @JoinColumn(name = "butchery_production_id")
    private ButcheryProduction butcheryProduction;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "butchery_receiving_item_id")
    private ButcheryReceivingItem butcheryReceivingItem;

    @Column(name = "required_qty")
    private BigDecimal requiredQty;

}
