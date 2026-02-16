package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.models.Transaction;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.repository.TransactionRepository;
import com.rcompany.tablecreater.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionReadDto createTransaction(TransactionCreateDto createDto) {
        // 1. Müştərini bazada axtarırıq
        Customer customer = customerRepository.findById(createDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Müştəri tapılmadı! ID: " + createDto.getCustomerId()));

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
        transaction.setPaymentDate(createDto.getPaymentDate());
        transaction.setPaidAmount(createDto.getPaidAmount());
        transaction.setPaidCurrency(createDto.getPaidCurrency());
        transaction.setDocumentImageUrl(createDto.getDocumentImageUrl());


        transaction.setHistoricalExchangeRate(createDto.getHistoricalExchangeRate());


        Transaction transactionSaved = transactionRepository.save(transaction);
        return mapToReadDto(transactionSaved);
    }

    private TransactionReadDto mapToReadDto(Transaction transaction) {
        TransactionReadDto transactionReadDto = modelMapper.map(transaction, TransactionReadDto.class);
        transactionReadDto.setCustomerName(transaction.getCustomer().getName());
        return transactionReadDto;
    }

}
