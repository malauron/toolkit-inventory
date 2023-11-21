package com.toolkit.inventory.Service;

import com.toolkit.inventory.Domain.ButcheryReleasing;
import com.toolkit.inventory.Domain.ButcheryReleasingItem;
import com.toolkit.inventory.Dto.ButcheryReleasingDto;
import com.toolkit.inventory.Projection.ButcheryReleasingSummaryView;

import java.util.Set;

public interface ButcheryReleasingService {
    ButcheryReleasingDto getButcheryReleasing(Long butcheryReleasingId);
    ButcheryReleasing save(ButcheryReleasingDto butcheryReleasingDto);
    ButcheryReleasingDto setButcheryReleasing(ButcheryReleasingDto butcheryReleasingDto);
    ButcheryReleasingDto setReleasingStatus(ButcheryReleasingDto butcheryReleasingDto);
    ButcheryReleasingDto deleteButcheryReleasingItem(ButcheryReleasingItem butcheryReleasingItem);
    ButcheryReleasingDto putButcheryReleasingItem(ButcheryReleasingItem butcheryReleasingItem);
    Set<ButcheryReleasingItem> getButcheryReleasingItems(Long butcheryReleasingId);
    Set<ButcheryReleasingSummaryView> getButcheryReleasingSummary(Long warehouseId);
}
