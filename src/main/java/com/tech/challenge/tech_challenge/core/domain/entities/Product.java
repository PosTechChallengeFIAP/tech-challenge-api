package com.tech.challenge.tech_challenge.core.domain.entities;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

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
    private Double price;
    
    @Column
    private Boolean active;

    public void validate() throws ValidationException {
        if (Objects.isNull(this.name) || this.name.isEmpty()) {
            throw new ValidationException("Invalid product name");
        }

        if (this.price <= 0) {
            throw new ValidationException("Invalid product price");
        }
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
