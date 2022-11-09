package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Domain.Warehouse;
import com.toolkit.inventory.Dto.ButcheryReceivingDto;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;

import java.util.Optional;
import java.util.Set;

public interface ButcheryReceivingService {
    ButcheryReceivingDto getButcheryReceiving(Long butcheryReceivingId);
    Optional<ButcheryReceivingItemView> getButcheryReceivingItem(Long butcheryReceivingItemId);
    Set<ButcheryReceivingItemView> getButcheryReceivingItemsByWarehouseId(Long warehouseId);
    Optional<ButcheryReceivingItem> findById(Long butcheryReceivingItemId);
    ButcheryReceiving save(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto setButcheryReceiving(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto setReceivingStatus(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto deleteButcheryReceivingItem(ButcheryReceivingItem butcheryReceivingItem);
    ButcheryReceivingDto putButcheryReceivingItem(ButcheryReceivingItem butcheryReceivingItem);
}
