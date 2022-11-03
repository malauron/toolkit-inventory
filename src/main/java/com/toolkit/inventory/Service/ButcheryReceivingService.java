package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryReceiving;
import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Dto.ButcheryReceivingDto;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;

import java.util.Optional;

public interface ButcheryReceivingService {
    ButcheryReceivingDto getButcheryReceiving(Long butcheryReceivingId);
    Optional<ButcheryReceivingItemView> getButcheryReceivingItem(Long butcheryReceivingItemId);
    Optional<ButcheryReceivingItem> findById(Long butcheryReceivingItemId);
    ButcheryReceiving save(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto setButcheryReceiving(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto setReceivingStatus(ButcheryReceivingDto butcheryReceivingDto);
    ButcheryReceivingDto deleteButcheryReceivingItem(ButcheryReceivingItem butcheryReceivingItem);
    ButcheryReceivingDto putButcheryReceivingItem(ButcheryReceivingItem butcheryReceivingItem);
}
