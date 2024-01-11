package com.presta.saving_management.controllers;

import com.presta.saving_management.models.Saving;
import com.presta.saving_management.services.SavingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("v1/")
@RestController
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService) {
        this.savingService = savingService;
    }


    @PostMapping("saving/save")
    public ResponseEntity<?> save(@Validated @RequestBody List<Map<String, Object>> listMap) {
        List<Saving> savings = savingService.save(listMap);

        return savings.isEmpty() ?
                ResponseEntity.status(400).body("Not saved, something went wrong") :
                ResponseEntity.status(201).body(savings);
    }

    @GetMapping("customer/savings")
    public ResponseEntity<?> getTotalSavingsPerCustomer(@RequestParam long customerId) {
        Double totalSavingPerCustomer = savingService.getTotalSavingsPerCustomer(customerId);

        return totalSavingPerCustomer != null ?
                ResponseEntity.ok().body(totalSavingPerCustomer) :
                ResponseEntity.status(204).body("Nothing to compute");
    }


    @GetMapping("customers/savings")
    public ResponseEntity<?> getTotalSavingsAcross() {
        Double totalSavings = savingService.getTotalSavingsAcross();

        return totalSavings != null ?
                ResponseEntity.ok().body(totalSavings) :
                ResponseEntity.status(204).body("Nothing to compute");
    }
}
