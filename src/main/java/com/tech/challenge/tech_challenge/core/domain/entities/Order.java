package com.tech.challenge.tech_challenge.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    private static final Consumer<? super OrderItem> OrderItem = null;

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

    @Enumerated(EnumType.ORDINAL)
    private EOrderStatus status;


    public Error validate() {
        if(!hasValidPrice()) {
            return new Error("Invalid price");
        }

        if(!hasValidOrderItems()) {
            return new Error("Invalid order items");
        }

        if(!hasValidClient()) {
            return new Error("Invalid client");
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
        OrderItem orderItemFound = findOrderItem(orderItem);
        
        if (orderItemFound != null) {
            int lastQuantity = orderItemFound.getQuantity();
            orderItemFound.setQuantity(lastQuantity+1);
        } else {
            this.orderItems.add(orderItem);
            orderItem.setOrder(this);
        }

    }

    public void removeItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    private OrderItem findOrderItem(OrderItem orderItem) {
        return orderItems.stream()
        .filter(item -> item.getId() == orderItem.getId())
        .findFirst()
        .orElse(null);
    }

    private boolean hasValidOrderItems() {
        Boolean valid = true;

        for (OrderItem item: orderItems) {
            if (item.getId() == null) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    private boolean hasValidClient() {
        return Objects.nonNull(client) ? Objects.nonNull(client.getId()) : true;
    }

    private boolean hasValidPrice() {
        return orderItems.size() > 0 ? price > 0 : true;
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
