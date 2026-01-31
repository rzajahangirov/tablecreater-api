package com.rcompany.tablecreater.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    // Müştərinin etdiyi bütün alqı-satqılar
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    // Müştəriyə özəl cədvəl strukturu (Sütunlar və Düsturlar)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomColumn> customColumns = new ArrayList<>();
}
