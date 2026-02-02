package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
