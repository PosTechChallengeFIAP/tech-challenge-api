package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class OrderItem {
    private UUID id;
    private Product product;
    private Integer quantity;

    public double getPrice(){
        return product.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem )) return false;
        return id != null && id.equals(((OrderItem) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void validate() throws ValidationException {
        if(Objects.isNull(product) || Objects.isNull(product.getId())){
            throw new ValidationException("Invalid Product");
        }

        if(Objects.isNull(quantity) || quantity < 1){
            throw new ValidationException("Quantity is lower than 1");
        }
    }
}
