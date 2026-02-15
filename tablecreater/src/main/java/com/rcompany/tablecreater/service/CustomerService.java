package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;

public interface CustomerService {
    CustomerReadDto createCustomer(CustomerCreateDto request);
}
