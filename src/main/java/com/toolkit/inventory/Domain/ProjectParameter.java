package com.toolkit.inventory.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "project_parameters")
public class ProjectParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_id")
    private Long parameterId;

    @Column(name = "equity_prc")
    private BigDecimal equityPrc;

    @Column(name = "financing_prc")
    private BigDecimal financingPrc;

    @Column(name = "brokerage_incentive")
    private BigDecimal brokerageIncentive;

    @Column(name = "brokerIncentive")
    private BigDecimal brokerIncentive;

    @Column(name = "equity_start_day")
    private Long equityStartDay;

    @Column(name = "equity_months")
    private Long equityMonths;

}
