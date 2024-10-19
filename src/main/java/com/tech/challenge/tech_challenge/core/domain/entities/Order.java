package com.tech.challenge.tech_challenge.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"client_id\"")
    private Client client;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<OrderItem> orderItems;

    @Transient
    private double price;

    public Error validate() {
        if(this.price == 0) {
            return new Error("Invalid price");
        }

        if (this.orderItems.isEmpty()) {
            return new Error("Order need to have more than one product");
        }

        return null;
    }

    public double getPrice() {
        this.price = 0;

        for (OrderItem orderItem : this.orderItems) {
            this.price += orderItem.getPrice();
        }

        return this.price;
    }

    public void addItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order )) return false;
        return id != null && id.equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
