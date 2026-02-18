package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;
import com.rcompany.tablecreater.dtos.transaction.TranslationExpenseDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
     TransactionReadDto createTransaction(TransactionCreateDto createDto, Long customerId);

     List<TransactionReadDto> getAllTranslationsByCustomer(Long customerId);

     TranslationExpenseDto calculateExpenseAndIncome(LocalDate from, LocalDate to);

     TranslationExpenseDto calculateCustomerExpenseAndIncome(Long customerId);
}
