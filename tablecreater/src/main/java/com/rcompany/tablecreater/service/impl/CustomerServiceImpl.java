package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.customer.CustomerCreateDto;
import com.rcompany.tablecreater.dtos.customer.CustomerReadDto;
import com.rcompany.tablecreater.dtos.customer.CustomerUpdateDto;
import com.rcompany.tablecreater.enums.CustomerType;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<CustomerReadDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }
        return customers.stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(Long id, CustomerType type) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setType(type);
        customerRepository.save(customer);
    }

    @Override
    public CustomerReadDto getCustomerBydId(Long id) {
        return mapToReadDto(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found")));
    }

    @Override
    public List<CustomerReadDto> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository
                .findByNameContainingIgnoreCaseOrPhoneContainingOrEmailContainingIgnoreCase(keyword, keyword, keyword);

        return customers.stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerReadDto updateCustomer(Long id, CustomerUpdateDto updateDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        if (updateDto.getName() != null) customer.setName(updateDto.getName());
        if (updateDto.getPhone() != null) customer.setPhone(updateDto.getPhone());
        if (updateDto.getEmail() != null) customer.setEmail(updateDto.getEmail());
        return mapToReadDto(customerRepository.save(customer));
    }


    private CustomerReadDto mapToReadDto(Customer customer) {
        return modelMapper.map(customer, CustomerReadDto.class);
    }
}
