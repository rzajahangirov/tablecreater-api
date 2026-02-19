package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;
import com.rcompany.tablecreater.dtos.customer.CustomerUpdateDto;
import com.rcompany.tablecreater.enums.CustomerType;

import java.util.List;

public interface CustomerService {
    CustomerReadDto createCustomer(CustomerCreateDto request);

    List<CustomerReadDto> getAllCustomers();

    void changeStatus(Long id, CustomerType type);

    CustomerReadDto getCustomerBydId(Long id);

    List<CustomerReadDto> searchCustomers(String keyword);

    CustomerReadDto updateCustomer(Long id, CustomerUpdateDto updateDto);

    void deleteCustomer(Long id);
}
