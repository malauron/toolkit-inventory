package com.toolkit.inventory.Dto;

import com.toolkit.inventory.Domain.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjectContractDto {
    private Long contractId;
    private ProjectClient client;
    private ProjectBroker broker;
    private ProjectBrokerage brokerage;
    private ProjectUnit unit;
    private String remarks;
    private String errorCode;
    private String errorDescription;
}
