package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.costumer.CustomerCreateDto;
import com.rcompany.tablecreater.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CustomerCreateDto customerCreateDto) {
        customerService.createCustomer(customerCreateDto);
        return ResponseEntity.ok().build();
    }
}
