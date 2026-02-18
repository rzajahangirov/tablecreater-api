package com.rcompany.tablecreater.dtos.transaction;

import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionUpdateReadDto {
    private LocalDate transactionDate;
    private String productName;
    private String receivingCompany;
    private BigDecimal weightTon;
    private BigDecimal pricePerTonRub;
    private TransportType transportType;
    private Integer vehicleCount;
    private BigDecimal pricePerVehicle;
    private PaymentCurrency paidCurrency;
    private BigDecimal paidAmount;
    private BigDecimal historicalExchangeRate;
    private String documentImageUrl;
    private Boolean isCompleted;
}
