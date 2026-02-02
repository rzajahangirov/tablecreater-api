package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.CustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFieldValueRepository extends JpaRepository<CustomFieldValue, Long> {
}
