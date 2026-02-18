package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByCustomer_Id(Long customerId);

    List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
}
