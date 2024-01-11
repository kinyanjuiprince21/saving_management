package com.presta.saving_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presta.saving_management.controllers.CustomerController;
import com.presta.saving_management.dto.CustomerDTO;
import com.presta.saving_management.models.Customer;
import com.presta.saving_management.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private CustomerService customerService;


    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().email("").firstName("").build();

        String requestBody = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/v1/customer/save").contentType("application/json")
                .content(requestBody)).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("email", "test@gmail.com");
        map.put("password", "avi900");
        map.put("firstName", "Test");

        Mockito.when(customerService.addCustomer(map)).thenReturn(new Customer(1L).toDTO());

        String requestBody = objectMapper.writeValueAsString(map);

        mockMvc.perform(post("/v1/customer/save").contentType("application/json")
                        .content(requestBody)).andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    public void testGetCustomersShouldReturn404NotFound() throws Exception {

        Mockito.when(customerService.getCustomers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetCustomersShouldReturn200Ok() throws Exception {

        CustomerDTO customer1 = CustomerDTO.builder().id(1L).email("david@gmail.com")
                .firstName("david").lastName("kin").build();

        CustomerDTO customer2 = CustomerDTO.builder().id(2L).email("test34@gmail.com")
                .firstName("Job").lastName("John").build();

        List<CustomerDTO> customers = List.of( customer1, customer2 );

        Mockito.when(customerService.getCustomers()).thenReturn(customers);

        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].email").value("david@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("test34@gmail.com"))
                .andDo(print());

    }

}
