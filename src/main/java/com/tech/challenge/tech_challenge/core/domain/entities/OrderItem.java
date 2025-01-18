package com.tech.challenge.tech_challenge.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Column(nullable = false)
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
