package com.presta.saving_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.presta.saving_management.controllers.TransactionController;
import com.presta.saving_management.dto.CustomerDTO;
import com.presta.saving_management.models.*;
import com.presta.saving_management.repositories.CustomerRepo;
import com.presta.saving_management.repositories.SavingRepo;
import com.presta.saving_management.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionService transactionService;

    @Test
    public void testSaveTransactionShouldReturn400BadRequest() throws Exception {
        Transaction transaction = Transaction.builder().customer(new Customer(0L)).saving(new Saving(0L)).build();

        String requestBody = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/v1/transaction/save").contentType("application/json").content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testSaveTransactionShouldReturn201Created() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("transactionType", "DEPOSIT");
        map.put("amount", "300");
        map.put("saving", "1");
        map.put("customer", "1");
        map.put("paymentMethod", "CASH");

        Mockito.when(transactionService.saveTransaction(map)).thenReturn(new Transaction(1L));

        String requestBody = objectMapper.writeValueAsString(map);

        mockMvc.perform(post("/v1/transaction/save").contentType("application/json").content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testGetTransactionsPerCustomerShouldReturn204() throws Exception {
        long customerId = 1;
        Mockito.when(transactionService.getTransactionsPerCustomer(customerId)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/v1/customer/transactions")
                        .param("customerId", String.valueOf(customerId)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetTransactionsPerCustomerShouldReturn200Ok() throws Exception {
        long customerId = 1;

        Transaction transaction1 = Transaction.builder().transactionType(TransactionType.valueOf("DEPOSIT"))
                        .amount(Double.parseDouble("300")).paymentMethod(PaymentMethod.valueOf("CASH")).customer(new Customer(Long.parseLong("1"))).saving(new Saving(Long.parseLong("1")))
                        .id(1L).build();

        Transaction transaction2 = Transaction.builder().transactionType(TransactionType.valueOf("DEPOSIT"))
                .amount(Double.parseDouble("400")).paymentMethod(PaymentMethod.valueOf("MPESA")).customer(new Customer(Long.parseLong("1"))).saving(new Saving(Long.parseLong("2")))
                .id(2L).build();

        List<Transaction> transactions = List.of(transaction1, transaction2);

        Mockito.when(transactionService.getTransactionsPerCustomer(customerId)).thenReturn(transactions);

        mockMvc.perform(get("/v1/customer/transactions")
                        .param("customerId", String.valueOf(customerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].amount").value(300))
                .andExpect(jsonPath("$[1].amount").value(400))
                .andDo(print());
    }
}
