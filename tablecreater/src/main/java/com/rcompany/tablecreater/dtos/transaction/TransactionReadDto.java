package com.rcompany.tablecreater.dtos.transaction;

import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionReadDto {
    private Long id;
    private String customerName;
    private LocalDate transactionDate;
    private LocalDate createdAt;
    private String productName;
    private String receivingCompany;
    private BigDecimal weightTon;
    private BigDecimal pricePerTonRub;
    private TransportType transportType;
    private Integer vehicleCount;
    private BigDecimal pricePerVehicle;
    private BigDecimal paidAmount;
    private PaymentCurrency paidCurrency;
    private String documentImageUrl;
    private BigDecimal historicalExchangeRate;
    private BigDecimal historicalTotalExpenseUsd;
    private BigDecimal historicalRemainingDebtUsd;
}
