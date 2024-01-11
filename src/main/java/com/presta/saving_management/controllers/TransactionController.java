package com.presta.saving_management.controllers;

import com.presta.saving_management.models.Transaction;
import com.presta.saving_management.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("v1/")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transaction/save")
    public ResponseEntity<?> save(@RequestBody Map<String, Object> map) {
        Transaction save = transactionService.saveTransaction(map);

        return save != null ?
                ResponseEntity.status(201).body(map) :
                ResponseEntity.status(400).body("Not saved");
    }


    @GetMapping("customer/transactions")
    public ResponseEntity<?> getTransactionsPerCustomer(@RequestParam long customerId) {
        List<Transaction> transactionList = transactionService.getTransactionsPerCustomer(customerId);

        return !transactionList.isEmpty() ?
                ResponseEntity.status(200).body(transactionList) :
                ResponseEntity.status(204).body("Transactions not found");
    }

}
