package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;
import com.rcompany.tablecreater.dtos.transaction.TranslationExpenseDto;
import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.exceptions.ApiException;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.models.Transaction;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.repository.TransactionRepository;
import com.rcompany.tablecreater.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionReadDto createTransaction(TransactionCreateDto createDto, Long customerId) {
        // 1. Müştərini bazada axtarırıq
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müştəri tapılmadı! ID: " + customerId));

        // 2. DTO-dan Entity-yə map edirik
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionDate(createDto.getTransactionDate());
        transaction.setProductName(createDto.getProductName());
        transaction.setReceivingCompany(createDto.getReceivingCompany());
        transaction.setWeightTon(createDto.getWeightTon());
        transaction.setPricePerTonRub(createDto.getPricePerTonRub());
        transaction.setTransportType(createDto.getTransportType());
        transaction.setVehicleCount(createDto.getVehicleCount());
        transaction.setPricePerVehicle(createDto.getPricePerVehicle());
        transaction.setPaidAmount(createDto.getPaidAmount());
        transaction.setPaidCurrency(createDto.getPaidCurrency());
        transaction.setDocumentImageUrl(createDto.getDocumentImageUrl());
        transaction.setHistoricalExchangeRate(createDto.getHistoricalExchangeRate());


        Transaction transactionSaved = transactionRepository.save(transaction);
        return mapToReadDto(transactionSaved);
    }

    @Override
    public List<TransactionReadDto> getAllTranslationsByCustomer(Long customerId) {
        List<Transaction> transactions = transactionRepository.findAllByCustomer_Id(customerId);
        if(transactions.isEmpty()) {
            return Collections.emptyList();
        }
        return transactions.stream()
                .map(transaction -> mapToReadDto(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public TranslationExpenseDto calculateExpenseAndIncome(LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new ApiException("Son tarix başlanğıc tarixdən əvvəl ola bilməz!");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(from, to);

        if (transactions.isEmpty()) {
            return new TranslationExpenseDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }

        return calculateExpenseAndIncome(transactions);
    }

    @Override
    public TranslationExpenseDto calculateCustomerExpenseAndIncome(Long customerId) {
        List<Transaction> transactions = transactionRepository.findAllByCustomer_Id(customerId);

        if (transactions.isEmpty()) {
            return new TranslationExpenseDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }
        return calculateExpenseAndIncome(transactions);
    }

    private TranslationExpenseDto calculateExpenseAndIncome(List<Transaction> transactions) {
        BigDecimal totalExpense = transactions.stream()
                .map(t -> t.getHistoricalTotalExpenseUsd() != null ? t.getHistoricalTotalExpenseUsd() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        BigDecimal totalPaid = transactions.stream()
                .map(t -> {
                    if (t.getPaidAmount() == null) return BigDecimal.ZERO;
                    if (t.getPaidCurrency().equals(PaymentCurrency.USD)) {
                        return t.getPaidAmount();
                    }
                    return t.getPaidAmount().multiply(t.getHistoricalExchangeRate());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal benefit = totalPaid.subtract(totalExpense);

        return new TranslationExpenseDto(totalExpense, totalPaid, benefit, transactions.size());
    }

    private TransactionReadDto mapToReadDto(Transaction transaction) {
        TransactionReadDto transactionReadDto = modelMapper.map(transaction, TransactionReadDto.class);
        transactionReadDto.setCustomerName(transaction.getCustomer().getName());
        return transactionReadDto;
    }

}
