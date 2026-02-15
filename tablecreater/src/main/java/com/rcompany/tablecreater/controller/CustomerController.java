package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;
import com.rcompany.tablecreater.payloads.ResponseDto;
import com.rcompany.tablecreater.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseDto<CustomerReadDto>> create(@RequestBody CustomerCreateDto request) {
        CustomerReadDto readDto = customerService.createCustomer(request);

        ResponseDto<CustomerReadDto> responseDto = new ResponseDto<>();
        responseDto.setData(readDto);
        responseDto.setMessage("Customer created");
        return ResponseEntity.ok(responseDto);
    }

}
