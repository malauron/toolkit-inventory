package com.toolkit.inventory.Security.Dto;

import com.toolkit.inventory.Security.Domain.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String username;
    private String password;
    private Set<Role> roles;

}
