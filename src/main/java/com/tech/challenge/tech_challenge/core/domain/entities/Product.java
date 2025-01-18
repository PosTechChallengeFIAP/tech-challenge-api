package com.tech.challenge.tech_challenge.core.domain.entities;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Product {
    private UUID id;
    private String name;
    private String description;
    private EProductCategory category;
    private Double price;
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
