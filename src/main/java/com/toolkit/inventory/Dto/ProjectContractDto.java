package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProjectContractDto {
    private Long contractId;
    private ProjectClient client;
    private ProjectBroker broker;
    private ProjectBrokerage brokerage;
    private ProjectUnit unit;
    private BigDecimal unitPrice;
    private BigDecimal reservationAmt;
    private BigDecimal ttlReservationPaid;
    private BigDecimal reservationBalance;
    private BigDecimal equityAmt;
    private BigDecimal ttlEquityPaid;
    private BigDecimal equityBalance;
    private BigDecimal financingAmt;
    private BigDecimal ttlFinancingPaid;
    private BigDecimal financingBalance;
    private BigDecimal ttlPayment;
    private BigDecimal ttlBalance;
    private String remarks;
    private Set<ProjectContractEquitySchedule> equitySchedules;
    private String errorCode;
    private String errorDescription;
}
