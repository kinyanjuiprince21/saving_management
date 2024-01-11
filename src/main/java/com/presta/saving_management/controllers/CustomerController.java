package com.presta.saving_management.controllers;

import com.presta.saving_management.dto.CustomerDTO;
import com.presta.saving_management.models.Customer;
import com.presta.saving_management.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("v1/")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("customer/save")
    public ResponseEntity<?> addCustomer(@RequestBody Map<String, Object> map) {
        CustomerDTO customer = customerService.addCustomer(map);

        return customer != null ?
                ResponseEntity.status(201).body(customer) :
                ResponseEntity.status(400).body("Customer not created");
    }


    @GetMapping("customers")
    public ResponseEntity<?> getCustomers() {
        List<CustomerDTO> customers = customerService.getCustomers();

        return !customers.isEmpty() ?
                ResponseEntity.status(200).body(customers) :
                ResponseEntity.status(204).body("Customers not found");
    }
}
