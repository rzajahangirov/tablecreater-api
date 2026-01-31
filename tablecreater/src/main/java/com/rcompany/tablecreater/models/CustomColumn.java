package com.rcompany.tablecreater.models;


import com.rcompany.tablecreater.enums.ColumnInputType;
import com.rcompany.tablecreater.enums.DataType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "custom_columns")
public class CustomColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Məs: "Gömrük Rüsumu" və ya "Sürücü Adı"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // === ƏSAS YENİLİK ===

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColumnInputType inputType; // MANUAL (Əllə) vs CALCULATED (Düsturla)

    @Column(length = 1000)
    private String formula;
    // Əgər CALCULATED olarsa, düstur bura yazılır.
    // Məsələn: "{weightTon} * 20" (Tonu 20-yə vur)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataType dataType; // Sütunun nəticə tipi (Money, Text, Number)

    private Integer sortOrder; // Cədvəldə neçənci yerdə dayansın
}