package com.toolkit.inventory.Security.Projection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toolkit.inventory.Security.Domain.Role;
import com.toolkit.inventory.Security.Domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "userView", types = {User.class })
public interface UserView {
    Long getUserId();
    String getUsername();

//    @Value("#{@roleRepository.findByUserId(target.userId)}")
    Set<Role> getRoles();
}
