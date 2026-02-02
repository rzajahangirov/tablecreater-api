package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
