package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.column.ColumnCreateDto;
import com.rcompany.tablecreater.dtos.column.ColumnReadDto;

import java.util.List;

public interface ColumnsService {
    void createColumn(ColumnCreateDto columnCreateDto);

    List<ColumnReadDto> getAllColumns();

    void deleteAll();
}
