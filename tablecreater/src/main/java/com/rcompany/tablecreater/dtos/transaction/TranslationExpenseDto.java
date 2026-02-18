package com.rcompany.tablecreater.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationExpenseDto {
    private BigDecimal totalExpenseUsd;
    private BigDecimal totalPaidUsd;
    private BigDecimal totalBenefitUsd;
    private long transactionCount;
}
