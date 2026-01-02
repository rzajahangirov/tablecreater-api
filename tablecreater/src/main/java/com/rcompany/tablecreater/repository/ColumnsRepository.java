package com.rcompany.tablecreater.repository;

import com.rcompany.tablecreater.models.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
}
