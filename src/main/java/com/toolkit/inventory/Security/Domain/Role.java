package com.toolkit.inventory.Security.Domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @noinspection JpaDataSourceORMInspection, JpaModelReferenceInspection
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @JsonIgnore
    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authorities",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")})
    private Set<Authority> authorities;

}
