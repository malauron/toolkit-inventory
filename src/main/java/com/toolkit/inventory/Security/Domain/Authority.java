package com.toolkit.inventory.Security.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @noinspection JpaDataSourceORMInspection, JpaModelReferenceInspection
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long authorityId;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
