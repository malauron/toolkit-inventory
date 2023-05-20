package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.CustomerGroup;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customerGroupView", types = { CustomerGroup.class })
public interface CustomerGroupView {
    Long getCustomerGroupId();
    String getCustomerGroupName();
}
