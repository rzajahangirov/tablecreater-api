package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.column.ColumnCreateDto;
import com.rcompany.tablecreater.dtos.column.ColumnReadDto;
import com.rcompany.tablecreater.enums.ColumnType;
import com.rcompany.tablecreater.models.Columns;
import com.rcompany.tablecreater.repository.ColumnsRepository;
import com.rcompany.tablecreater.service.ColumnsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnsServiceImpl implements ColumnsService {
    private final ColumnsRepository columnsRepository;
    private final ModelMapper modelMapper;
    @Override
    public void createColumn(ColumnCreateDto columnCreateDto) {
        Columns column = modelMapper.map(columnCreateDto, Columns.class);
        try {
            ColumnType type = ColumnType.valueOf(columnCreateDto.getColumnTypeDto().toUpperCase());
            column.setColumnType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown column type: " + columnCreateDto.getColumnTypeDto());
        }
        columnsRepository.save(column);
    }

    @Override
    public List<ColumnReadDto> getAllColumns() {
        List<Columns> columns = columnsRepository.findAll();
        List<ColumnReadDto> columnReadDtos = new ArrayList<>();
        for (Columns column : columns) {
            ColumnReadDto columnReadDto = mapToDto(column);
            columnReadDtos.add(columnReadDto);
        }
        return columnReadDtos;
    }

    @Override
    public void deleteAll() {
        columnsRepository.deleteAll();
    }

    private ColumnReadDto mapToDto(Columns column) {
        ColumnReadDto columnReadDto = new ColumnReadDto();
        columnReadDto.setId(column.getId());
        columnReadDto.setColumnName(column.getColumnName());
        columnReadDto.setColumnType(column.getColumnType().name());
        return columnReadDto;
    }
}
