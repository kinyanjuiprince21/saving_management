package com.presta.saving_management.services;

import com.presta.saving_management.models.Customer;
import com.presta.saving_management.models.Saving;
import com.presta.saving_management.repositories.CustomerRepo;
import com.presta.saving_management.repositories.SavingRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SavingService {

    private final SavingRepo savingRepo;
    private final CustomerRepo customerRepo;

    public SavingService(SavingRepo savingRepo, CustomerRepo customerRepo) {
        this.savingRepo = savingRepo;
        this.customerRepo = customerRepo;
    }

    /**
     * Brief summary of method
     * @param listMap represents the data type that the data will be passed in the method.List of maps
     *            is used as several objects will be passed to the method
     * @return it returns a list of the savings right after they are saved in the database
     */
    public List<Saving> save(List<Map<String, Object>> listMap) {

        List<Saving> savings = listMap.stream().map(saving -> {
            Saving saving1 = new Saving();
            saving1.setInitialBalance(Double.parseDouble(String.valueOf(saving.get("initialBalance"))));
            saving1.setBalance(Double.parseDouble(String.valueOf(saving.get("initialBalance"))));
            saving1.setSavingName(String.valueOf(saving.get("savingName")));
            Customer customer = customerRepo.findById(Long.valueOf(String.valueOf(saving.get("customer")))).orElse(null);

            saving1.setCustomer(customer);

            saving1 =  savingRepo.save(saving1);
            System.out.println("I am here");
            System.out.println(saving1);
            return saving1;
        }).toList();
        System.out.println(savings);
        return savings;
    }

    /**
     * Brief summary of method
     * @param customerId it is used to search savings based on a specific customer
     * @return it returns the computed total value of the savings per customer
     */
    public Double getTotalSavingsPerCustomer(long customerId) {
        List<Saving> savings = savingRepo.findSavingsByCustomer_Id(customerId);

        return savings.stream().mapToDouble(Saving::getBalance).sum();
    }

    /**
     * Brief summary of method
     * @return it returns the computed total value of the savings of all the customers in the system
     */
    public Double getTotalSavingsAcross() {
        return savingRepo.findAll().stream()
                .mapToDouble(Saving::getBalance).sum();
    }
}
