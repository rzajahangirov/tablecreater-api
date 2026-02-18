package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.transaction.*;
import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.exceptions.ApiException;
import com.rcompany.tablecreater.exceptions.ResourceNotFoundException;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.models.Transaction;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.repository.TransactionRepository;
import com.rcompany.tablecreater.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    private final String UPLOAD_DIR = "uploads/";

    @Override
    @Transactional
    public TransactionReadDto createTransaction(TransactionCreateDto createDto, Long customerId) {
        // 1. Müştərini bazada axtarırıq
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müştəri tapılmadı! ID: " + customerId));

        String documentPath = null;
        if (createDto.getDocument() != null && !createDto.getDocument().isEmpty()) {
            documentPath = saveFile(createDto.getDocument()); // Faylı saxla və yolunu al
        }

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
        transaction.setDocumentImageUrl(documentPath);
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

    @Override
    @Transactional
    public TransactionReadDto updateTransaction(Long id, TransactionUpdateDto updateDto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));

        String documentPath = null;
        if (updateDto.getDocument() != null && !updateDto.getDocument().isEmpty()) {
            documentPath = saveFile(updateDto.getDocument());

        }

        transaction.setTransactionDate(updateDto.getTransactionDate());
        transaction.setProductName(updateDto.getProductName());
        transaction.setReceivingCompany(updateDto.getReceivingCompany());
        transaction.setWeightTon(updateDto.getWeightTon());
        transaction.setPricePerTonRub(updateDto.getPricePerTonRub());
        transaction.setTransportType(updateDto.getTransportType());
        transaction.setVehicleCount(updateDto.getVehicleCount());
        transaction.setPricePerVehicle(updateDto.getPricePerVehicle());
        transaction.setPaidCurrency(updateDto.getPaidCurrency());
        transaction.setPaidAmount(updateDto.getPaidAmount());
        transaction.setHistoricalExchangeRate(updateDto.getHistoricalExchangeRate());
        transaction.setDocumentImageUrl(documentPath);
        transaction.setIsCompleted(updateDto.getIsCompleted());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return mapToReadDto(updatedTransaction);
    }

    @Override
    public TransactionUpdateReadDto getTransactionForUpdate(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));

        return TransactionUpdateReadDto.builder()
                .transactionDate(transaction.getTransactionDate())
                .productName(transaction.getProductName())
                .receivingCompany(transaction.getReceivingCompany())
                .weightTon(transaction.getWeightTon())
                .pricePerTonRub(transaction.getPricePerTonRub())
                .transportType(transaction.getTransportType())
                .vehicleCount(transaction.getVehicleCount())
                .pricePerVehicle(transaction.getPricePerVehicle())
                .paidCurrency(transaction.getPaidCurrency())
                .paidAmount(transaction.getPaidAmount())
                .historicalExchangeRate(transaction.getHistoricalExchangeRate())
                .documentImageUrl(transaction.getDocumentImageUrl())
                .isCompleted(transaction.getIsCompleted())
                .build();
    }

    private String saveFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }
            String fileName = UUID.randomUUID().toString() + fileExtension;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return "/" + UPLOAD_DIR + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
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
        transactionReadDto.setCustomerId(transaction.getCustomer().getId());
        return transactionReadDto;
    }

}
