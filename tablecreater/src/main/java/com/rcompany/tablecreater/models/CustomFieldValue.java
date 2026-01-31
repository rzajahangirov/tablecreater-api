package com.rcompany.tablecreater.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "custom_field_values")
public class CustomFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hansı əməliyyata aiddir?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    // Hansı sütuna aiddir? (Header)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_column_id", nullable = false)
    private CustomColumn customColumn;

    // Dəyər (String saxlayırıq, lazım olanda convert edirik)
    // Əgər ColumnInputType = MANUAL-dırsa, istifadəçi bura yazır.
    // Əgər CALCULATED-dirsə, bu sahə boş qala bilər (çünki real vaxtda hesablanır)
    // və ya cache məqsədi ilə nəticə bura yazıla bilər.
    @Column(columnDefinition = "TEXT")
    private String value;
}
