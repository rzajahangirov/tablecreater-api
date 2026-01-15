package com.rcompany.tablecreater.controller;

import com.rcompany.tablecreater.dtos.data.DataCreateDto;
import com.rcompany.tablecreater.dtos.data.DataReadDto;
import com.rcompany.tablecreater.service.DatasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {
    private final DatasService datasService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<List<DataReadDto>> create(@RequestBody DataCreateDto dataCreateDto, @PathVariable("customerId") Long customerId) {
        List<DataReadDto> readDto = datasService.creatDatas(dataCreateDto, customerId);
        return ResponseEntity.ok(readDto);
    }
}
