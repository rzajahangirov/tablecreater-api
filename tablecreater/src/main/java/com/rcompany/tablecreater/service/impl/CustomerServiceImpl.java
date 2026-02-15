package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CustomerReadDto createCustomer(CustomerCreateDto request) {
        // 1. Müştərini yarat və yadda saxla
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());

        Customer savedCustomer = customerRepository.save(customer);

        return mapToReadDto(savedCustomer);
    }

    private CustomerReadDto mapToReadDto(Customer customer) {
        return modelMapper.map(customer, CustomerReadDto.class);
    }
}
