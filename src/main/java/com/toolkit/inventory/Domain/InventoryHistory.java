package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "inventory_history")
public class InventoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_history_id")
    private Long inventoryHistoryId;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @JsonManagedReference
    @OneToMany(mappedBy = "inventoryHistory", cascade = CascadeType.ALL)
    private Set<InventoryHistoryItem> inventoryHistoryItems = new HashSet<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

}
