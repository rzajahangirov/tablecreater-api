package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;
import com.rcompany.tablecreater.dtos.customer.CustomerUpdateDto;
import com.rcompany.tablecreater.enums.CustomerType;
import com.rcompany.tablecreater.payloads.ResponseDto;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<ResponseDto<CustomerReadDto>> create(@RequestBody CustomerCreateDto request) {
        CustomerReadDto readDto = customerService.createCustomer(request);

        ResponseDto<CustomerReadDto> responseDto = new ResponseDto<>();
        responseDto.setData(readDto);
        responseDto.setMessage("Customer created");
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<CustomerReadDto>>> getAll() {
        List<CustomerReadDto> readDtos = customerService.getAllCustomers();
        ResponseDto<List<CustomerReadDto>> responseDto = new ResponseDto<>();
        responseDto.setData(readDtos);
        responseDto.setMessage(
                !readDtos.isEmpty() ? "Successfully" : "Transaction is null"
        );
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CustomerReadDto>> getById(@PathVariable Long id) {
        CustomerReadDto readDto = customerService.getCustomerBydId(id);

        ResponseDto<CustomerReadDto> responseDto = new ResponseDto<>();
        responseDto.setData(readDto);
        responseDto.setMessage("Customer found");
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeCustomerStatus(
            @PathVariable Long id,
            @RequestParam CustomerType type) {

        customerService.changeStatus(id, type);
        return ResponseEntity.ok("Customer status updated");
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<CustomerReadDto>>> search(@RequestParam String keyword) {
        List<CustomerReadDto> results = customerService.searchCustomers(keyword);

        ResponseDto<List<CustomerReadDto>> response = new ResponseDto<>();
        response.setData(results);
        response.setMessage(results.isEmpty() ? "No customers found" : "Customers found successfully");

        return ResponseEntity.ok(response);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseDto<CustomerReadDto>> update(@PathVariable Long id, @RequestBody CustomerUpdateDto updateDto) {
        CustomerReadDto readDto = customerService.updateCustomer(id, updateDto);

        ResponseDto<CustomerReadDto> responseDto = new ResponseDto<>();
        responseDto.setData(readDto);
        responseDto.setMessage("Customer updated");
        return ResponseEntity.ok(responseDto);
    }



}
