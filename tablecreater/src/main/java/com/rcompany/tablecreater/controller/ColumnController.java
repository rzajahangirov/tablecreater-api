package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.column.ColumnCreateDto;
import com.rcompany.tablecreater.dtos.column.ColumnReadDto;
import com.rcompany.tablecreater.service.ColumnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/column")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnsService columnsService;
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody ColumnCreateDto columnCreateDto) {
        columnsService.createColumn(columnCreateDto);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<ColumnReadDto>> getColumn() {
        List<ColumnReadDto> columnReadDtoList = columnsService.getAllColumns();
        return ResponseEntity.ok(columnReadDtoList);
    }
    @DeleteMapping
    public ResponseEntity<Void> delete(){
        columnsService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
