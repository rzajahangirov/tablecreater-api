package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.data.DataCreateDto;
import com.rcompany.tablecreater.dtos.data.DataReadDto;
import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.models.Datas;
import com.rcompany.tablecreater.repository.ColumnsRepository;
import com.rcompany.tablecreater.repository.CustomerRepository;
import com.rcompany.tablecreater.repository.DatasRepository;
import com.rcompany.tablecreater.service.DatasService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatasServiceImpl implements DatasService {
    private final DatasRepository datasRepository;
    private final CustomerRepository customerRepository;
    private final ColumnsRepository columnsRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DataReadDto> creatDatas(DataCreateDto dataCreateDto, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Customer not found"));
        List<DataReadDto> dataReadDtos = new ArrayList<>();
        for (Map.Entry<Long, String> map: dataCreateDto.getColumnIdAndData().entrySet()) {
            Datas newData = new Datas();
            newData.setCustomer(customer);
            newData.setColumn(columnsRepository.findById(map.getKey()).orElseThrow(()->new RuntimeException("Column not found")));
            newData.setData(map.getValue());
            datasRepository.save(newData);
            dataReadDtos.add(mapToDataReadDto(newData));

        }
        return dataReadDtos;
    }
    private DataReadDto mapToDataReadDto(Datas datas) {
        DataReadDto dataReadDto = new DataReadDto();
        dataReadDto.setId(datas.getId());
        dataReadDto.setData(datas.getData());
        dataReadDto.setCustomerName(datas.getCustomer().getName());
        dataReadDto.setColumnName(datas.getColumn().getColumnName());
        return dataReadDto;
    }
}
