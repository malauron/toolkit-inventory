package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryBatch;
import com.toolkit.inventory.Domain.VendorWarehouse;
import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Repository.ButcheryBatchRepository;
import com.toolkit.inventory.Repository.VendorWarehouseRepository;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ButcheryBatchServiceImp implements ButcheryBatchService{

    private ButcheryBatchRepository butcheryBatchRepository;
    private VendorWarehouseRepository vendorWarehouseRepository;
    private UserRepository userRepository;

    @Autowired
    public ButcheryBatchServiceImp(
            ButcheryBatchRepository butcheryBatchRepository,
            VendorWarehouseRepository vendorWarehouseRepository,
            UserRepository userRepository
    ) {
        this.butcheryBatchRepository = butcheryBatchRepository;
        this.vendorWarehouseRepository = vendorWarehouseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ButcheryBatchDto save(ButcheryBatchDto butcheryBatchDto) {

        ButcheryBatch butcheryBatch;

        if (butcheryBatchDto.getButcheryBatchId() > 0) {

            Optional<ButcheryBatch> optBatch = this.butcheryBatchRepository
                    .findById(butcheryBatchDto.getButcheryBatchId());

            butcheryBatch = optBatch.get();
            butcheryBatch.setBatchStatus(butcheryBatchDto.getBatchStatus());
            butcheryBatch.setHasInventory(butcheryBatchDto.getHasInventory());
            butcheryBatch.setIsOpen(butcheryBatchDto.getIsOpen());

        } else {

            butcheryBatch = new ButcheryBatch();
            butcheryBatch.setBatchStatus("Unposted");
            butcheryBatch.setHasInventory(false);
            butcheryBatch.setIsOpen(true);

            Optional<User> user = this.userRepository.findByUsername(butcheryBatchDto.getCreatedBy().getUsername());
            butcheryBatch.setCreatedBy(user.get());

        }

        Optional<VendorWarehouse> vendorWarehouse = this.vendorWarehouseRepository
                .findByVendorWarehouseId(butcheryBatchDto.getVendorWarehouse().getVendorWarehouseId());

        butcheryBatch.setVendorWarehouse(vendorWarehouse.get());
        butcheryBatch.setDateReceived(butcheryBatchDto.getDateReceived());
        butcheryBatch.setRemarks(butcheryBatchDto.getRemarks());

        butcheryBatch = this.butcheryBatchRepository.saveAndFlush(butcheryBatch);

        butcheryBatchDto.setButcheryBatchId(butcheryBatch.getButcheryBatchId());
        butcheryBatchDto.setBatchStatus(butcheryBatch.getBatchStatus());
        butcheryBatchDto.setHasInventory(butcheryBatch.getHasInventory());
        butcheryBatchDto.setIsOpen(butcheryBatch.getIsOpen());
        butcheryBatchDto.setCreatedBy(null);

        return butcheryBatchDto;
    }
}
