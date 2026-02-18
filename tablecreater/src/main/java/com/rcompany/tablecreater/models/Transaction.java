package com.rcompany.tablecreater.models;

import com.rcompany.tablecreater.enums.PaymentCurrency;
import com.rcompany.tablecreater.enums.TransportType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // === INPUT DATA (Dəyişməyən Faktlar) ===

    @Column(nullable = false)
    private LocalDate transactionDate; // İstifadəçinin seçdiyi tarix

    @Column(updatable = false)
    private LocalDate createdAt;       // Sistem tarixi

    private String productName;        // Malın adı
    private String receivingCompany;   // Qəbul edən firma

    // --- Malın Dəyəri ---
    @Column(precision = 19, scale = 2)
    private BigDecimal weightTon;      // Neçə ton

    @Column(precision = 19, scale = 2)
    private BigDecimal pricePerTonRub; // 1 tonun qiyməti (Rubl)

    // --- Nəqliyyat ---
    @Enumerated(EnumType.STRING)
    private TransportType transportType; // TRUCK (Dollar) və ya SHIP (Rubl)

    private Integer vehicleCount;      // Maşın sayı

    @Column(precision = 19, scale = 2)
    private BigDecimal pricePerVehicle; // Maşın qiyməti (Tırsa $, Gəmidsə ₽)

    // --- Ödəniş ---
    @Column(precision = 19, scale = 2)
    private BigDecimal paidAmount;      // Verilən pul (məbləğ)

    @Enumerated(EnumType.STRING)
    private PaymentCurrency paidCurrency; // Verilən pulun valyutası (USD/RUB)

    private String documentImageUrl;

    // === HISTORICAL DATA (Keçmiş Hesabatlar Üçün Sabitlər) ===

    // Əməliyyat yaranan günkü məzənnə (Dəyişməz!)
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal historicalExchangeRate;

    // O vaxtkı məzənnə ilə hesablanmış ümumi xərc (USD)
    @Column(precision = 19, scale = 2)
    private BigDecimal historicalTotalExpenseUsd;

    // O vaxtkı məzənnə ilə qalan pul (USD)
    @Column(precision = 19, scale = 2)
    private BigDecimal historicalRemainingDebtUsd;

    // === DYNAMIC DATA LINK ===
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomFieldValue> customValues = new ArrayList<>();

    private Boolean isCompleted = Boolean.FALSE;

    // === AVTOMATİK HESABLAMA (Yalnız Historical Data üçün) ===
    @PrePersist
    @PreUpdate
    public void calculateHistoricalData() {
        if (createdAt == null) createdAt = LocalDate.now();

        // 1. Malın qiyməti (Rubl)
        BigDecimal goodsCostRub = (weightTon != null && pricePerTonRub != null)
                ? weightTon.multiply(pricePerTonRub)
                : BigDecimal.ZERO;

        // Convert to USD
        goodsCostRub = goodsCostRub.multiply(historicalExchangeRate);

        // 2. Nəqliyyat Xərci (Rubla çevrilməsi)
        BigDecimal transportCostRub = BigDecimal.ZERO;
        if (vehicleCount != null && pricePerVehicle != null) {
            BigDecimal totalTransportRaw = pricePerVehicle.multiply(BigDecimal.valueOf(vehicleCount));

            if (transportType != TransportType.TRUCK) {
                //GƏMİ (RUB) -> USD çevir (Həmin günün məzənnəsi ilə)
                transportCostRub = totalTransportRaw.multiply(historicalExchangeRate);
            } else {
                // TIR (USD) -> Olduğu kimi qalır
                transportCostRub = totalTransportRaw;
            }
        }

        // 3. Yekun Xərc (USD) - Historical
        this.historicalTotalExpenseUsd = goodsCostRub.add(transportCostRub);

        // 4. Ödənilən Pulun USD qarşılığı - Historical
        BigDecimal paidInUsd = BigDecimal.ZERO;

        if (paidAmount != null) {
            if (paidCurrency == PaymentCurrency.RUB) {
                paidInUsd = paidAmount.multiply(historicalExchangeRate);
            } else {
                paidInUsd = paidAmount;
            }
        }


        // 5. Qalan Borc - Historical
        this.historicalRemainingDebtUsd = paidInUsd.subtract(this.historicalTotalExpenseUsd);
    }
}
