package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.CustomColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomColumnRepository extends JpaRepository<CustomColumn, Long> {
}
