package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ProjectContractDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProjectContractServiceImp implements ProjectContractService {

    final ProjectContractRepository contractRepository;
    final ProjectUnitRepository unitRepository;
    final ProjectClientRepository clientRepository;
    final ProjectBrokerRepository brokerRepository;
    final ProjectBrokerageRepository brokerageRepository;

    @Autowired
    public ProjectContractServiceImp(
            ProjectContractRepository contractRepository,
            ProjectUnitRepository unitRepository,
            ProjectClientRepository clientRepository,
            ProjectBrokerRepository brokerRepository,
            ProjectBrokerageRepository brokerageRepository
    ){
        this.contractRepository = contractRepository;
        this.unitRepository = unitRepository;
        this.clientRepository = clientRepository;
        this.brokerRepository = brokerRepository;
        this.brokerageRepository = brokerageRepository;
    }
    @Override
    @Transactional
    public ProjectContractDto save(ProjectContractDto contractDto) throws Exception {

        contractDto.setErrorCode(null);
        contractDto.setErrorDescription(null);

        ProjectUnit unit = null;
        ProjectContract contract = null;

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

                if (contractDto.getUnit().getVersion() != optUnit.get().getVersion()) {
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
                    contractDto.setErrorDescription("Unit is not longer available.");

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

            contract = new ProjectContract();

            contract.setUnit(contractDto.getUnit());
            contract.setClient(contractDto.getClient());
            if (contractDto.getBroker() != null) contract.setBroker(contractDto.getBroker());
            if (contractDto.getBrokerage() != null)  contract.setBrokerage(contractDto.getBrokerage());
            contract.setUnitPrice(unit.getUnitPrice());
            contract.setReservationAmt(unit.getReservationAmt());
            contract.setTtlReservationPaid(BigDecimal.ZERO);
            contract.setReservationBalance(BigDecimal.ZERO);
            contract.setEquityPct(BigDecimal.ZERO);
            contract.setEquityAmt(BigDecimal.ZERO);
            contract.setTtlEquityPaid(BigDecimal.ZERO);
            contract.setEquityBalance(BigDecimal.ZERO);
            contract.setFinancingPct(BigDecimal.ZERO);
            contract.setFinancingAmt(BigDecimal.ZERO);
            contract.setTtlFinancingPaid(BigDecimal.ZERO);
            contract.setFinancingBalance(BigDecimal.ZERO);
            contract.setTtlBalance(unit.getUnitPrice());
            contract.setTtlPayment(BigDecimal.ZERO);
            contract.setIsCancelled(false);

            this.unitRepository.saveAndFlush(unit);

        } else {

            Optional<ProjectContract> optContract = this.contractRepository.findById(contractDto.getContractId());

            if (optContract.isPresent()) {
                contract = optContract.get();
            } else {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Contract not found.");
                return contractDto;
            }

        }

        contract.setRemarks(contractDto.getRemarks());

        this.contractRepository.save(contract);

        contractDto.setContractId(contract.getContractId());
        contractDto.setUnit(unit);

        return contractDto;

    }

}
