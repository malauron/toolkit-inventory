package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.Vendor;
import com.toolkit.inventory.Domain.Warehouse;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "butcheryReceivingView", types = {ButcheryReceiving.class})
public interface ButcheryReceivingView {
    Long getButcheryReceivingId();
    ButcheryBatch getButcheryBatch();
    Warehouse getWarehouse();
    Vendor getVendor();
    String getReferenceCode();
    BigDecimal getTotalAmount();
    String getReceivingStatus();
    Date getDateCreated();
}
