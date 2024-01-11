package com.presta.saving_management.repositories;

import com.presta.saving_management.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionByCustomer_Id(long id);
}
