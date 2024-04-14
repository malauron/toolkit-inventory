package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ProjectBroker;
import com.toolkit.inventory.Domain.ProjectClient;
import com.toolkit.inventory.Domain.ProjectContract;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "projectContractCurrentView", types = {ProjectContract.class})
public interface ProjectContractCurrentView {
    Long getContractId();
    ProjectClient getClient();
    ProjectBroker getBroker();
    BigDecimal getUnitPrice();
    BigDecimal getEquityBalance();
}
