package com.toolkit.inventory.Projection;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Domain.VendorWarehouse;
import com.toolkit.inventory.Security.Projection.UserView;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "butcheryBatchView", types = { ButcheryBatch.class })
public interface ButcheryBatchView {
    Long getButcheryBatchId();
    Date getDateReceived();
    VendorWarehouse getVendorWarehouse();
    String getRemarks();
    String getBatchStatus();
    Boolean getHasInventory();
    Boolean getIsOpen();
    Date getDateCreated();
}

