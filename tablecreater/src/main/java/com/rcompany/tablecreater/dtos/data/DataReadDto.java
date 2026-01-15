package com.rcompany.tablecreater.dtos.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataReadDto {
    private Long id;
    private String data;
    private String customerName;
    private String columnName;
}
