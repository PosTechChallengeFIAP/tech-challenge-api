package com.tech.challenge.tech_challenge.core.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
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

    public Error validate() {
        if (Objects.isNull(this.name) || this.name.isEmpty()) {
            return new Error("Invalid name");
        }

        if (this.price <= 0) {
            return new Error("Invalid price");
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product )) return false;
        return id != null && id.equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
