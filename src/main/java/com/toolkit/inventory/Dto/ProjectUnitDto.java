package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjectUnitDto {
    private Long unitId;
    private String unitCode;
    private String unitDescription;
    private BigDecimal unitPrice;
    private BigDecimal reservationAmt;
    private ProjectUnitClass unitClass;
    private ProjectUnitStatus unitStatus;
    private ProjectContract currentContract;
    private Project project;
}
