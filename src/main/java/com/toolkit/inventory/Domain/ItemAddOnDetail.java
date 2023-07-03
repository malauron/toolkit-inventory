package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "item_add_on_details")
public class ItemAddOnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_add_on_detail_id")
    private Long itemAddOnDetailId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonManagedReference
    @OneToMany(mappedBy = "itemAddOnDetail", cascade = CascadeType.ALL)
    private Set<ItemAddOnContent> itemAddOnContents = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "max_no_of_items")
    private Long maxNoOfItems;

    public void addItemAddOnContent(ItemAddOnContent itemAddOnContent) {
        if (itemAddOnContent != null) {
            if (this.itemAddOnContents == null) {
                this.itemAddOnContents = new HashSet<>();
            }

            this.itemAddOnContents.add(itemAddOnContent);
            itemAddOnContent.setItemAddOnDetail(this);
        }
    }
}
