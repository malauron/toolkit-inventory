package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ProjectClient;
import com.toolkit.inventory.Domain.ProjectPayment;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "projectPaymentView", types = {ProjectPayment.class})
public interface ProjectPaymentView {
    Long getPaymentId();
    ProjectClient getClient();
    BigDecimal getTtlAmt();
    String getReferenceNo();
    Date getDateReceived();
}
