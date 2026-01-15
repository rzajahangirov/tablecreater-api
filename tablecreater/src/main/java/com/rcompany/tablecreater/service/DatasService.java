package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.data.DataCreateDto;
import com.rcompany.tablecreater.dtos.data.DataReadDto;

import java.util.List;

public interface DatasService {
    List<DataReadDto> creatDatas(DataCreateDto dataCreateDto, Long customerId);
}
