package com.rcompany.tablecreater.dtos.data;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataCreateDto {
    private Map<Long, String> columnIdAndData;
}
