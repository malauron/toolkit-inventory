package com.toolkit.inventory.Controller;

import com.toolkit.inventory.Dto.ButcheryBatchDto;
import com.toolkit.inventory.Service.ButcheryBatchService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/butcheryBatches")
    public ButcheryBatchDto save(@RequestBody ButcheryBatchDto butcheryBatchDto) {
        return this.butcheryBatchService.save(butcheryBatchDto);
    }
}
