package com.presta.saving_management.repositories;

import com.presta.saving_management.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Long> {
    List<Saving> findSavingsByCustomer_Id(long id);
}
