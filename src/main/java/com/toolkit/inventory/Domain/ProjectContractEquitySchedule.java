package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "project_contract_equity_schedules")
public class ProjectContractEquitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "contractId")
    private ProjectContract contract;

    @Column(name = "payable_equity")
    private BigDecimal payableEquity;

    @Column(name = "equity_paid")
    private BigDecimal equityPaid;

    @Column(name = "due_date")
    private Date dueDate;

}
