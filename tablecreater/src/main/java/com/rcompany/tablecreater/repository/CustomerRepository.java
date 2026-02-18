package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCaseOrPhoneContainingOrEmailContainingIgnoreCase(
            String name, String phone, String email);
}
