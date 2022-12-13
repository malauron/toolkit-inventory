package com.toolkit.inventory.Security.Domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @noinspection JpaDataSourceORMInspection, JpaModelReferenceInspection
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Singular
  @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
          joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
  private Set<Role> roles;

  @Transient
  private Set<Authority> authorities;

  public Set<Authority> getAuthorities() {
    return this.roles.stream()
            .map(Role::getAuthorities)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
  }

  @Builder.Default
  @Column(name = "account_non_expired")
  private Boolean accountNonExpired = true;

  @Builder.Default
  @Column(name = "account_non_locked")
  private Boolean accountNonLocked = true;

  @Builder.Default
  @Column(name = "credentials_non_expired")
  private Boolean credentialsNonExpired = true;

  @Builder.Default
  @Column(name = "enabled")
  private Boolean enabled = true;

}
