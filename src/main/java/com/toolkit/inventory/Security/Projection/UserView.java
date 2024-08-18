package com.toolkit.inventory.Security.Projection;

import com.toolkit.inventory.Security.Domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userView", types = {User.class })
public interface UserView {
    Long getUserId();
    String getUsername();
    String getFullName();
    String getEmail();
}
