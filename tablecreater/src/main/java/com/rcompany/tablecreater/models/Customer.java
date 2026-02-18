package com.rcompany.tablecreater.models;

import com.rcompany.tablecreater.enums.CustomerType;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private CustomerType type = CustomerType.ACTIVE;

    // Müştərinin etdiyi bütün alqı-satqılar
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

}
