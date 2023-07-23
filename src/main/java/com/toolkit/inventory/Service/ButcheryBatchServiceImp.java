package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.*;
import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Repository.*;
import com.toolkit.inventory.Security.Domain.User;
import com.toolkit.inventory.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ButcheryBatchServiceImp implements ButcheryBatchService{

    private ButcheryBatchRepository butcheryBatchRepository;
    private ButcheryBatchDetailRepository butcheryBatchDetailRepository;
    private ButcheryBatchDetailItemRepository butcheryBatchDetailItemRepository;
    private VendorWarehouseRepository vendorWarehouseRepository;
    private VendorRepository vendorRepository;
    private ItemRepository itemRepository;
    private UomRepository uomRepository;
    private ItemUomRepository itemUomRepository;
    private UserRepository userRepository;

    @Autowired
    public ButcheryBatchServiceImp(
            ButcheryBatchRepository butcheryBatchRepository,
            ButcheryBatchDetailRepository butcheryBatchDetailRepository,
            ButcheryBatchDetailItemRepository butcheryBatchDetailItemRepository,
            VendorWarehouseRepository vendorWarehouseRepository,
            VendorRepository vendorRepository,
            ItemRepository itemRepository,
            UomRepository uomRepository,
            ItemUomRepository itemUomRepository,
            UserRepository userRepository
    ) {
        this.butcheryBatchRepository = butcheryBatchRepository;
        this.butcheryBatchDetailRepository = butcheryBatchDetailRepository;
        this.butcheryBatchDetailItemRepository = butcheryBatchDetailItemRepository;
        this.vendorWarehouseRepository = vendorWarehouseRepository;
        this.vendorRepository = vendorRepository;
        this.itemRepository = itemRepository;
        this.uomRepository = uomRepository;
        this.itemUomRepository = itemUomRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
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
            butcheryBatch.setIsOpen(false);
            butcheryBatch.setButcheryBatchDetails(butcheryBatchDto.getButcheryBatchDetails());

            Optional<User> user = this.userRepository.findByUsername(butcheryBatchDto.getCreatedBy().getUsername());
            butcheryBatch.setCreatedBy(user.get());

        }

        Optional<VendorWarehouse> vendorWarehouse = this.vendorWarehouseRepository
                .findByVendorWarehouseId(butcheryBatchDto.getVendorWarehouse().getVendorWarehouseId());

        butcheryBatch.setVendorWarehouse(vendorWarehouse.get());
        butcheryBatch.setDateReceived(butcheryBatchDto.getDateReceived());
        butcheryBatch.setRemarks(butcheryBatchDto.getRemarks());

//        butcheryBatch = this.butcheryBatchRepository.saveAndFlush(butcheryBatch);

        if (butcheryBatchDto.getButcheryBatchId() == 0) {
//            ButcheryBatch newBatch = butcheryBatch;
            butcheryBatchDto.getButcheryBatchDetails().forEach(batchDetail -> {

                Optional<Vendor> optVendor = this.vendorRepository.findById(batchDetail.getVendor().getVendorId());

//                batchDetail.setButcheryBatch(newBatch);
                batchDetail.setVendor(optVendor.get());

//                ButcheryBatchDetail newDetail = this.butcheryBatchDetailRepository.saveAndFlush(batchDetail);

                batchDetail.getButcheryBatchDetailItems().forEach(batchDetailItem -> {

                    Optional<Item> optItem = this.itemRepository.findById(batchDetailItem.getItem().getItemId());
                    Optional<Uom> optUom = this.uomRepository.findById(batchDetailItem.getRequiredUom().getUomId());

//                    batchDetailItem.setButcheryBatchDetail(newDetail);
                    batchDetailItem.setItem(optItem.get());
                    batchDetailItem.setBaseUom(optItem.get().getUom());
                    batchDetailItem.setBaseQty(new BigDecimal(1));
                    batchDetailItem.setRequiredUom(optUom.get());

                    if (batchDetailItem.getBaseUom().getUomId() != batchDetailItem.getRequiredUom().getUomId()) {
                        ItemUomId itemUomId = new ItemUomId();
                        itemUomId.setItemId(optItem.get().getItemId());
                        itemUomId.setUomId(optUom.get().getUomId());

                        Optional<ItemUom> optItemUom = this.itemUomRepository.findById(itemUomId);

                        batchDetailItem.setBaseQty(optItemUom.get().getQuantity());
                    }

//                    this.butcheryBatchDetailItemRepository.saveAndFlush(batchDetailItem);
                });
//                ButcheryBatchDetail newDetail = this.butcheryBatchDetailRepository.saveAndFlush(batchDetail);
            });
        }

        butcheryBatch = this.butcheryBatchRepository.saveAndFlush(butcheryBatch);

        butcheryBatchDto.setButcheryBatchId(butcheryBatch.getButcheryBatchId());
        butcheryBatchDto.setBatchStatus(butcheryBatch.getBatchStatus());
        butcheryBatchDto.setHasInventory(butcheryBatch.getHasInventory());
        butcheryBatchDto.setIsOpen(butcheryBatch.getIsOpen());
        butcheryBatchDto.setCreatedBy(null);

        return butcheryBatchDto;
    }
}
