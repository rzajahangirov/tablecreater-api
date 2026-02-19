package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.transaction.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
     TransactionReadDto createTransaction(TransactionCreateDto createDto, Long customerId);

     List<TransactionReadDto> getAllTranslationsByCustomer(Long customerId);

     TranslationExpenseDto calculateExpenseAndIncome(LocalDate from, LocalDate to);

     TranslationExpenseDto calculateCustomerExpenseAndIncome(Long customerId);

     TransactionReadDto updateTransaction(Long id, TransactionUpdateDto updateDto);

     TransactionUpdateReadDto getTransactionForUpdate(Long id);

     void deleteTransaction(Long id);

     ByteArrayInputStream exportCustomerTransactions(Long customerId);
}
