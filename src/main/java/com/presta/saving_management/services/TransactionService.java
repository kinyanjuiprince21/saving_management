package com.presta.saving_management.services;

import com.presta.saving_management.error.TransactionNotAllowed;
import com.presta.saving_management.models.*;
import com.presta.saving_management.repositories.CustomerRepo;
import com.presta.saving_management.repositories.SavingRepo;
import com.presta.saving_management.repositories.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final SavingRepo savingRepo;
    private final CustomerRepo customerRepo;
    private final TransactionRepo transactionRepo;

    public TransactionService(SavingRepo savingRepo, CustomerRepo customerRepo, TransactionRepo transactionRepo) {
        this.savingRepo = savingRepo;
        this.customerRepo = customerRepo;
        this.transactionRepo = transactionRepo;
    }

    /**
     * Brief summary of method
     * @param map represents the data type that the data will be passed in the method.Map
     *            is used to represent the json data that will passed to the method from the api
     * @return it returns a transaction object that can be used to perform
     * other useful operations. This is useful in creating multiple transactions objects
     */
    public Transaction saveTransaction(Map<String, Object> map) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.valueOf(String.valueOf(map.get("transactionType"))));
        transaction.setAmount(Double.parseDouble(String.valueOf(map.get("amount"))));
        Saving saving = savingRepo.findById(Long.valueOf(String.valueOf(map.get("saving")))).orElse(null);
        transaction.setSaving(saving);
        Customer customer = customerRepo.findById(Long.valueOf(String.valueOf(map.get("customer")))).orElse(null);

        transaction.setCustomer(customer);
        transaction.setPaymentMethod(PaymentMethod.valueOf(String.valueOf(map.get("paymentMethod"))));

        transaction = transactionRepo.save(transaction);

        assert saving != null;
        if(transaction.getTransactionType() == TransactionType.DEPOSIT) {
            saving.setBalance(saving.getBalance() + transaction.getAmount());
        }
        if (transaction.getTransactionType() == TransactionType.WITHDRAW){

            if(saving.getBalance() == 0) {
                saving.setBalance(saving.getBalance());
                try {
                    throw new TransactionNotAllowed("You do not have any money to withdraw. Please deposit!");
                } catch (TransactionNotAllowed e) {
                    throw new RuntimeException(e);
                }
            } else {
                saving.setBalance(saving.getBalance() - transaction.getAmount());
            }
        }
        savingRepo.save(saving);

        return transaction;

    }

    /**
     * Brief summary of method
     * @param customerId this is passed to search all the transactions that
     *                   a certain customer did.
     * @return it returns a list of the transactions found from the database
     */
    public List<Transaction> getTransactionsPerCustomer(long customerId) {
        return transactionRepo.findTransactionByCustomer_Id(customerId);
    }
}
