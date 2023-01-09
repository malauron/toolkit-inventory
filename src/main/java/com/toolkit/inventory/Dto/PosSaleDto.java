package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Domain.Customer;
import com.toolkit.inventory.Domain.PosSaleItem;
import com.toolkit.inventory.Domain.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class PosSaleDto {
    private Long posSaleId;
    private Warehouse warehouse;
    private Customer customer;
    private Set<PosSaleItem> posSaleItems;
    private PosSaleItem posSaleItem;
    private BigDecimal totalAmount;
    private String saleStatus;
    private Date dateCreated;
    private String errorDescription;
}
