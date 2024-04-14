package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Domain.ProjectUnitStatus;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "projectUnitListView", types = {ProjectUnit.class})
public interface ProjectUnitListView {
    Long getUnitId();
    String getUnitCode();
    String getUnitDescription();
    BigDecimal getUnitPrice();
    BigDecimal getReservationAmt();
    ProjectUnitStatus getUnitStatus();
    ProjectContractCurrentView getCurrentContract();
}
