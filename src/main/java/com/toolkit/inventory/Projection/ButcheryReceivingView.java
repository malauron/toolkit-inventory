package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.*;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "butcheryReceivingView", types = {ButcheryReceiving.class})
public interface ButcheryReceivingView {
    Long getButcheryReceivingId();
    Warehouse getWarehouse();
    VendorWarehouse getVendorWarehouse();
    String getReferenceCode();
    BigDecimal getTotalKg();
    String getReceivingStatus();
    Date getDateCreated();
}
