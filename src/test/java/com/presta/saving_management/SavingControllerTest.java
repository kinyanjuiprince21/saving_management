package com.presta.saving_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presta.saving_management.controllers.SavingController;
import com.presta.saving_management.dto.CustomerDTO;
import com.presta.saving_management.models.Customer;
import com.presta.saving_management.models.Saving;
import com.presta.saving_management.repositories.SavingRepo;
import com.presta.saving_management.services.CustomerService;
import com.presta.saving_management.services.SavingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SavingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SavingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SavingService savingService;
    @Mock
    private SavingRepo savingRepo;


    @Test
    public void testSaveSavingsShouldReturn400BadRequest() throws Exception {
        Saving saving = Saving.builder().savingName("").customer(new Customer(0L)).build();

        String requestBody = objectMapper.writeValueAsString(saving);

        mockMvc.perform(post("/v1/saving/save").contentType("application/json")
                        .content(requestBody)).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testSaveSavingsShouldReturn201Created() throws Exception {
        List<Map<String, Object>> listMap = new ArrayList<>();
        CustomerDTO customer1 = CustomerDTO.builder().id(1L).email("david@gmail.com")
                .firstName("david").lastName("kin").build();

        Map<String, Object> saving1 = new HashMap<>();
        saving1.put("initialBalance", "20");
        saving1.put("savingName", "Education");
        saving1.put("customer", customer1.id());
        listMap.add(saving1);

        Map<String, Object> saving2 = new HashMap<>();
        saving2.put("initialBalance", "30");
        saving2.put("savingName", "Personal");
        saving2.put("customer", customer1.id());
        listMap.add(saving2);

        Map<String, Object> saving3 = new HashMap<>();
        saving3.put("initialBalance", "40");
        saving3.put("savingName", "Vacation");
        saving3.put("customer", customer1.id());
        listMap.add(saving3);

        String requestBody = objectMapper.writeValueAsString(listMap);
        Mockito.when(savingService.save(Mockito.anyList())).thenAnswer((Answer<List<Saving>>) invocation -> {

            // Mocking the behavior to return a list with one element
            Saving firstItem = Saving.builder().id(1L).savingName("Education")
                    .initialBalance(20).build();
            List<Saving> resultList = Collections.singletonList(firstItem);

            return resultList;
        });

        mockMvc.perform(post("/v1/saving/save").contentType("application/json").content(requestBody)).andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].savingName").value("Education"))
                .andExpect(jsonPath("$[0].initialBalance").value(20))
                .andDo(print());

    }

    @Test
    public void testGetTotalSavingsPerCustomerShouldReturn204NotContent() throws Exception {
        long customerId = 1;
        Mockito.when(savingService.getTotalSavingsPerCustomer(customerId)).thenReturn(null);

        mockMvc.perform(get("/v1/customer/savings")
                        .param("customerId", String.valueOf(customerId)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetTotalSavingsPerCustomerShouldReturn200Ok() throws Exception {
        long customerId = 1;

        Mockito.when(savingService.getTotalSavingsPerCustomer(customerId)).thenReturn(20.0);

        mockMvc.perform(get("/v1/customer/savings")
                        .param("customerId", String.valueOf(customerId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/json"))
                        .andDo(print());
    }

    @Test
    public void testGetTotalSavingsAcrossShouldReturn204NotContent() throws Exception {
        Mockito.when(savingService.getTotalSavingsAcross()).thenReturn(null);

        mockMvc.perform(get("/v1/customers/savings"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetTotalSavingsAcrossShouldReturn200Ok() throws Exception {
        Mockito.when(savingService.getTotalSavingsAcross()).thenReturn(60.0);

        mockMvc.perform(get("/v1/customers/savings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

}
