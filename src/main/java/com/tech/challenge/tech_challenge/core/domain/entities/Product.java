package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private EProductCategory category;

    @Column(nullable = false)
    private double price;

    public Product() {}

    public Error validate() {
        if (this.name.isEmpty() || this.name.equals("")) {
            return new Error("Invalid name");
        }

        if (this.price <= 0) {
            return new Error("Invalid price");
        }

        return null;
    }
}
