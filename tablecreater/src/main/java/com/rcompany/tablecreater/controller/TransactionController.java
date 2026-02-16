package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.transaction.TransactionCreateDto;
import com.rcompany.tablecreater.dtos.transaction.TransactionReadDto;
import com.rcompany.tablecreater.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionReadDto> createTransaction(@Valid @RequestBody TransactionCreateDto createDto) {
        return ResponseEntity.ok(transactionService.createTransaction(createDto));
    }
}
