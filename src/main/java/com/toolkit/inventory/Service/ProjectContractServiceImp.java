package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ProjectContractDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectContractServiceImp implements ProjectContractService {

    final ProjectContractRepository contractRepository;
    final ProjectContractEquityScheduleRepository scheduleRepository;
    final ProjectUnitRepository unitRepository;
    final ProjectClientRepository clientRepository;
    final ProjectBrokerRepository brokerRepository;
    final ProjectBrokerageRepository brokerageRepository;

    final ProjectParameterRepository parameterRepository;

    @Autowired
    public ProjectContractServiceImp(
            ProjectContractRepository contractRepository,
            ProjectContractEquityScheduleRepository scheduleRepository,
            ProjectUnitRepository unitRepository,
            ProjectClientRepository clientRepository,
            ProjectBrokerRepository brokerRepository,
            ProjectBrokerageRepository brokerageRepository,
            ProjectParameterRepository parameterRepository
    ){
        this.contractRepository = contractRepository;
        this.scheduleRepository = scheduleRepository;
        this.unitRepository = unitRepository;
        this.clientRepository = clientRepository;
        this.brokerRepository = brokerRepository;
        this.brokerageRepository = brokerageRepository;
        this.parameterRepository = parameterRepository;
    }
    @Override
    @Transactional
    public ProjectContractDto save(ProjectContractDto contractDto) throws Exception {

        contractDto.setErrorCode(null);
        contractDto.setErrorDescription(null);

        ProjectUnit unit;
        ProjectContract contract;

        ProjectParameter param = this.parameterRepository.findById(1L).get();

        if (contractDto.getContractId() == null || contractDto.getContractId() == 0) {

            if (contractDto.getUnit() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No unit specified.");
                return contractDto;
            }

            if (contractDto.getUnit().getUnitId() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No unit specified.");
                return contractDto;
            }

            if (contractDto.getClient() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No client specified.");
                return contractDto;
            }

            if (contractDto.getClient().getClientId() == null || contractDto.getClient().getClientId() == 0) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No client specified.");
                return contractDto;
            }

            Optional<ProjectUnit> optUnit = this.unitRepository.findById(contractDto.getUnit().getUnitId());

            if (optUnit.isEmpty()) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Unit not found");
                return contractDto;
            }  else {

                if (!contractDto.getUnit().getVersion().equals(optUnit.get().getVersion())) {
                    contractDto.setErrorCode("1");
                    contractDto.setErrorDescription("The record you are trying to save contains stale data.");
                    return contractDto;
                }

                if (optUnit.get().getUnitStatus().equals(ProjectUnitStatus.AVAILABLE)) {
                    unit = optUnit.get();

                    unit.setUnitStatus(ProjectUnitStatus.RESERVED);

                    contractDto.setUnit(unit);
                } else {
                    contractDto.setErrorCode("1");
                    contractDto.setErrorDescription("Unit is not available.");

                    return contractDto;
                }

            }

            Optional<ProjectClient> optClient = this.clientRepository.findById(contractDto.getClient().getClientId());

            if (optClient.isEmpty()) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Client not found.");
                return contractDto;
            } else {
                contractDto.setClient(optClient.get());
            }

            if (contractDto.getBroker() != null) {

                if (contractDto.getBroker().getBrokerId() != null) {

                    Optional<ProjectBroker> optBroker = this.brokerRepository.findById(contractDto.getBroker().getBrokerId());

                    if (optBroker.isEmpty()) {
                        contractDto.setErrorCode("1");
                        contractDto.setErrorDescription("Broker not found.");
                        return contractDto;
                    } else {
                        contractDto.setBroker(optBroker.get());
                    }

                }

            }

            if (contractDto.getBrokerage() != null) {

                if (contractDto.getBrokerage().getBrokerageId() != null) {

                    Optional<ProjectBrokerage> optBrokerage = this.brokerageRepository.findById(contractDto.getBrokerage().getBrokerageId());

                    if (optBrokerage.isEmpty()) {
                        contractDto.setErrorCode("1");
                        contractDto.setErrorDescription("Brokerage not found.");
                        return contractDto;
                    } else {
                        contractDto.setBrokerage(optBrokerage.get());
                    }

                }

            }

            Set<ProjectContractEquitySchedule> equitySchedules;
            BigDecimal unitPrice;
            BigDecimal equityPrc;
            BigDecimal equityAmt;
            BigDecimal equityBalance;
            BigDecimal financingPrc;
            BigDecimal financingAmt;
            BigDecimal financingBalance;

            equityPrc = param.getEquityPrc();
            unitPrice = unit.getUnitPrice();
            equityAmt = equityPrc.multiply(unitPrice);
            equityBalance = equityAmt;
            financingPrc = param.getFinancingPrc();
            financingAmt = unitPrice.subtract(equityAmt);
            financingBalance = financingAmt;

            contract = new ProjectContract();

            contract.setUnit(contractDto.getUnit());
            contract.setClient(contractDto.getClient());

            if (contractDto.getBroker().getBrokerId() != null) {
                contract.setBroker(contractDto.getBroker());
            }
            if (contractDto.getBrokerage().getBrokerageId() != null)  {
                contract.setBrokerage(contractDto.getBrokerage());
            }

            contract.setUnitPrice(unit.getUnitPrice());
            contract.setReservationAmt(unit.getReservationAmt());
            contract.setTtlReservationPaid(BigDecimal.ZERO);
            contract.setReservationBalance(unit.getReservationAmt());
            contract.setEquityPct(equityPrc);
            contract.setEquityAmt(equityAmt);
            contract.setTtlEquityPaid(BigDecimal.ZERO);
            contract.setEquityBalance(equityBalance);
            contract.setFinancingPct(financingPrc);
            contract.setFinancingAmt(financingAmt);
            contract.setTtlFinancingPaid(BigDecimal.ZERO);
            contract.setFinancingBalance(financingBalance);
            contract.setTtlBalance(unit.getUnitPrice());
            contract.setTtlPayment(BigDecimal.ZERO);
            contract.setIsCancelled(false);
            contract.setRemarks(contractDto.getRemarks());

            this.contractRepository.saveAndFlush(contract);

            equitySchedules = generateEquitySchedule(contract, LocalDate.now(), param.getEquityStartDay(), contract.getEquityAmt(), param.getEquityMonths());

            unit.setCurrentContract(contract);

            this.unitRepository.saveAndFlush(unit);

            contractDto.setContractId(contract.getContractId());
            contractDto.setUnitPrice(contract.getUnitPrice());
            contractDto.setReservationAmt(contract.getReservationAmt());
            contractDto.setTtlReservationPaid(contract.getTtlReservationPaid());
            contractDto.setReservationBalance(contract.getReservationBalance());
            contractDto.setEquityAmt(contract.getEquityAmt());
            contractDto.setTtlEquityPaid(contract.getTtlEquityPaid());
            contractDto.setEquityBalance(contract.getEquityBalance());
            contractDto.setFinancingAmt(contract.getFinancingAmt());
            contractDto.setTtlFinancingPaid(contract.getTtlFinancingPaid());
            contractDto.setFinancingBalance(contract.getFinancingBalance());
            contractDto.setTtlPayment(contract.getTtlPayment());
            contractDto.setTtlBalance(contract.getTtlBalance());
            contractDto.setUnit(unit);
            contractDto.setEquitySchedules(equitySchedules);

        } else {

            Optional<ProjectContract> optContract = this.contractRepository.findById(contractDto.getContractId());

            if (optContract.isPresent()) {
                contract = optContract.get();
            } else {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Contract not found.");
                return contractDto;
            }

            contract.setRemarks(contractDto.getRemarks());

            this.contractRepository.saveAndFlush(contract);

        }

        return contractDto;

    }

    private Set<ProjectContractEquitySchedule> generateEquitySchedule(
            ProjectContract contract,
            LocalDate reservationDate,
            Long daysCount,
            BigDecimal equityAmt,
            Long monthsToPay) {

        LocalDate payDate = reservationDate.plusDays(daysCount);
        int dayNo = payDate.getDayOfMonth();
        BigDecimal payableEquity = equityAmt.divide(BigDecimal.valueOf(monthsToPay), 0, RoundingMode.HALF_UP);
        Set<ProjectContractEquitySchedule> equitySchedules = new HashSet<>();

        while (monthsToPay > 0) {


            ProjectContractEquitySchedule equitySchedule = new ProjectContractEquitySchedule();

            equitySchedule.setContract(contract);
            equitySchedule.setEquityPaid(BigDecimal.ZERO);

            if (dayNo > 28) {
                try {
                    payDate = payDate.withDayOfMonth(dayNo);
                } catch (DateTimeException e1) {
                    try {
                        payDate = payDate.withDayOfMonth(30);
                    } catch (DateTimeException e2) {
                        try {
                            payDate = payDate.withDayOfMonth(29);
                        } catch (DateTimeException e3) {
                            payDate = payDate.withDayOfMonth(28);
                        }
                    }
                }
            }
            equitySchedule.setDueDate(Date.valueOf(payDate));

            if (equityAmt.compareTo(payableEquity) > 0) {
                if (monthsToPay > 1) {
                    equitySchedule.setPayableEquity(payableEquity);
                    equityAmt = equityAmt.subtract(payableEquity);
                } else {
                    equitySchedule.setPayableEquity(equityAmt);
                    equityAmt = BigDecimal.ZERO;
                }
            } else {
                equitySchedule.setPayableEquity(equityAmt);
                equityAmt = BigDecimal.ZERO;
            }

            scheduleRepository.save(equitySchedule);

            equitySchedules.add(equitySchedule);
            payDate = payDate.plusMonths(1);
            monthsToPay --;
        }

        return equitySchedules;

    }

}
