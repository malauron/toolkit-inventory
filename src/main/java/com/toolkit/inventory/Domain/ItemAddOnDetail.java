package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "item_add_on_details")
public class ItemAddOnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_add_on_detail_id")
    private Long itemAddOnDetailId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "max_no_of_items")
    private Long maxNoOfItems;

}
