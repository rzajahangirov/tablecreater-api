package com.rcompany.tablecreater.dtos.transaction;

import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.enums.TransportType;
import jakarta.validation.constraints.*;
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
public class TransactionCreateDto {
    @NotNull(message = "Tarix qeyd edilməlidir")
    private LocalDate transactionDate;


    @NotBlank(message = "Məhsul adı boş ola bilməz")
    private String productName;

    @NotBlank(message = "Qəbul edən firma qeyd edilməlidir")
    private String receivingCompany;

    @NotNull(message = "Çəki (Ton) qeyd edilməlidir")
    @Positive(message = "Çəki mütləq 0-dan böyük olmalıdır")
    private BigDecimal weightTon;

    @NotNull(message = "Qiymət qeyd edilməlidir")
    @PositiveOrZero(message = "Qiymət mənfi ola bilməz")
    private BigDecimal pricePerTonRub;

    @NotNull(message = "Nəqliyyat növü seçilməlidir")
    private TransportType transportType;

    @Min(value = 1, message = "Maşın/Gəmi sayı ən az 1 olmalıdır")
    private Integer vehicleCount;

    @PositiveOrZero(message = "Nəqliyyat qiyməti mənfi ola bilməz")
    private BigDecimal pricePerVehicle;

    @NotNull(message = "Ödəniş valyutası mütləqdir")
    private PaymentCurrency paidCurrency;

    @PositiveOrZero(message = "Ödənilən məbləğ mənfi ola bilməz")
    private BigDecimal paidAmount;

    @NotNull(message = "Məzənnə mütləqdir")
    @Positive(message = "Məzənnə 0-dan böyük olmalıdır")
    private BigDecimal historicalExchangeRate;

    private String documentImageUrl;
}
