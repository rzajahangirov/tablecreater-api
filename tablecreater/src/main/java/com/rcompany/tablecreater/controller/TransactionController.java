package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;
import com.rcompany.tablecreater.dtos.transaction.TranslationExpenseDto;
import com.rcompany.tablecreater.payloads.ResponseDto;
import com.rcompany.tablecreater.repository.CustomFieldValueRepository;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.repository.TransactionRepository;
import com.rcompany.tablecreater.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final CustomFieldValueRepository customFieldValueRepository;
    private final CustomerRepository customerRepository;

    @PostMapping("/{customerId}")
    public ResponseEntity<TransactionReadDto> createTransaction(@PathVariable Long customerId, @Valid @RequestBody TransactionCreateDto createDto) {
        return ResponseEntity.ok(transactionService.createTransaction(createDto, customerId));
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseDto<List<TransactionReadDto>>> getTransaction(@PathVariable Long customerId) {
        List<TransactionReadDto> readDtos = transactionService.getAllTranslationsByCustomer(customerId);

        ResponseDto<List<TransactionReadDto>> responseDto = new ResponseDto<>();
        responseDto.setData(readDtos);
        responseDto.setMessage(
                !readDtos.isEmpty() ? "Successfully" : "Transaction is null"
        );
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/expense-income")
    public ResponseEntity<ResponseDto<TranslationExpenseDto>> getExpenseIncome(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        TranslationExpenseDto expenseDto = transactionService.calculateExpenseAndIncome(from, to);

        ResponseDto<TranslationExpenseDto> responseDto = new ResponseDto<>();
        responseDto.setData(expenseDto);
        responseDto.setMessage(expenseDto.getTransactionCount() > 0 ? "Successfully" : "No transactions found in this range");

        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/expense-income/{customerId}")
    public ResponseEntity<ResponseDto<TranslationExpenseDto>> getExpenseIncome(@PathVariable Long customerId) {
        TranslationExpenseDto expenseDto = transactionService.calculateCustomerExpenseAndIncome(customerId);

        ResponseDto<TranslationExpenseDto> responseDto = new ResponseDto<>();
        responseDto.setData(expenseDto);
        responseDto.setMessage(expenseDto.getTransactionCount() > 0 ? "Successfully" : "No transactions found in this range");

        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/reset")
    public ResponseEntity<Void> reset() {

        customFieldValueRepository.deleteAll();
        transactionRepository.deleteAll();
        customerRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }

}
