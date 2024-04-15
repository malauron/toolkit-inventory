package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ProjectBroker;
import com.toolkit.inventory.Domain.ProjectBrokerage;
import com.toolkit.inventory.Domain.ProjectClient;
import com.toolkit.inventory.Domain.ProjectUnit;
import com.toolkit.inventory.Dto.ProjectContractDto;
import com.toolkit.inventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public ProjectContractDto save(ProjectContractDto contractDto){
        if (contractDto.getContractId() == null) {

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

            if (contractDto.getClient().getClientId() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No client specified.");
                return contractDto;
            }

            if (contractDto.getBroker() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No broker specified.");
                return contractDto;
            }

            if (contractDto.getBroker().getBrokerId() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No broker specified.");
                return contractDto;
            }

            if (contractDto.getBrokerage() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No brokerage specified.");
                return contractDto;
            }

            if (contractDto.getBrokerage().getBrokerageId() == null) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("No brokerage specified.");
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
                contractDto.setUnit(optUnit.get());
            }

            Optional<ProjectClient> optClient = this.clientRepository.findById(contractDto.getClient().getClientId());
            if (optClient.isEmpty()) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Client not found.");
                return contractDto;
            } else {
                contractDto.setClient(optClient.get());
            }

            Optional<ProjectBroker> optBroker = this.brokerRepository.findById(contractDto.getBroker().getBrokerId());
            if (optBroker.isEmpty()) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Broker not found.");
                return contractDto;
            } else {
                contractDto.setBroker(optBroker.get());
            }

            Optional<ProjectBrokerage> optBrokerage = this.brokerageRepository.findById(contractDto.getBrokerage().getBrokerageId());
            if (optBrokerage.isEmpty()) {
                contractDto.setErrorCode("1");
                contractDto.setErrorDescription("Brokerage not found.");
                return contractDto;
            } else {
                contractDto.setBrokerage(optBrokerage.get());
            }

        }
        return null;
    }
}
