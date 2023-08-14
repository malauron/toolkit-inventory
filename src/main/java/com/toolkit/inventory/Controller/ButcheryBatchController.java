package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Service.ButcheryBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ButcheryBatchController {
    private ButcheryBatchService butcheryBatchService;

    @Autowired
    public ButcheryBatchController(
            ButcheryBatchService butcheryBatchService
    ){
        this.butcheryBatchService = butcheryBatchService;
    }

    @GetMapping("/butcheryBatches")
    public ButcheryBatchDto getButcheryBatch(@RequestParam Long butcheryBatchId) {
        return this.butcheryBatchService.getButcheryBatch(butcheryBatchId);
    }

    @GetMapping("/butcheryBatches/inventorySummary")
    public Page getButcheryBatchInventorySummary(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Long vendorWarehouseId,
            @RequestParam String itemName){

        return this.butcheryBatchService.getButcheryBatchInventorySummary(page, size, vendorWarehouseId, itemName);
    };

    @PostMapping("/butcheryBatches")
    public ButcheryBatchDto save(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.save(butcheryBatchDto);
    }

    @PutMapping("/butcheryBatches")
    public ButcheryBatchDto updateButcheryBatch(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.updateBatchStatus(butcheryBatchDto);
    }

    @PostMapping("/butcheryBatchDetails")
    public ButcheryBatchDto saveButcheryBatchDetail(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.saveButcheryBatchDetail(butcheryBatchDto);
    }

    @PostMapping("/butcheryBatchDetailItems")
    public ButcheryBatchDto saveButcheryBatchDetailItem(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.saveButcheryBatchDetailItem(butcheryBatchDto);
    }

    @DeleteMapping("/butcheryBatchDetails")
    public ButcheryBatchDto deleteButcheryBatchDetail(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.deleteButcheryBatchDetail(butcheryBatchDto);
    }

    @DeleteMapping("/butcheryBatchDetailItems")
    public ButcheryBatchDto deleteButcheryBatchDetailItem(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.deleteButcheryBatchDetailItem(butcheryBatchDto);
    }
}
