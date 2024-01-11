package com.presta.saving_management.services;

import com.presta.saving_management.dto.CustomerDTO;
import com.presta.saving_management.models.Customer;
import com.presta.saving_management.repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Brief summary of method
     * @param map represents the data type that the data will be passed in the method.Map
     *            is used to represent the json data that will passed to the method from the api
     * @return it returns a customer data transfer object that is useful for data protection
     * of sensitive info such as the password
     */
    public CustomerDTO addCustomer(Map<String, Object> map) {

        Customer customer = new Customer();
        customer.setEmail(String.valueOf(map.get("email")));
        customer.setPassword(String.valueOf(map.get("password")));
        customer.setFirstName(String.valueOf(map.get("firstName") != null ? map.get("firstName") : ""));
        customer.setLastName(String.valueOf(map.get("lastName") != null ? map.get("lastName") : ""));
        customer.setMemberNumber(String.valueOf(map.get("memberNumber") != null ? map.get("memberNumber") : ""));
        customer.setPhoneNumber(String.valueOf(map.get("phoneNumber") != null ? map.get("phoneNumber") : ""));

        return customerRepo.save(customer).toDTO();


    }

    /**
     * Brief summary of method
     * @return it returns a list of the customer data transfer object
     */
    public List<CustomerDTO> getCustomers() {

        return customerRepo.findAll().stream().map(Customer::toDTO).toList();
    }
}
