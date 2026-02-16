package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;

public interface TransactionService {
     TransactionReadDto createTransaction(TransactionCreateDto createDto);
}
