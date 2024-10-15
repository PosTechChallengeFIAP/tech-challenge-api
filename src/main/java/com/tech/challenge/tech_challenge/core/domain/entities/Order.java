package com.tech.challenge.tech_challenge.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "productOrderRelation",
            joinColumns = {@JoinColumn(name = "orderId")},
            inverseJoinColumns = {@JoinColumn(name = "productId")})
    private Set<Product> products;

    @Transient
    private double price;

    public Order() {}

    public Error validate() {
        if(this.price == 0) {
            return new Error("Invalid price");
        }

        if (this.products.isEmpty()) {
            return new Error("Order need to have more than one product");
        }

        return null;
    }

    public double getPrice() {
        this.price = 0;

        for (Product product : this.products) {
            this.price += product.getPrice();        
        }

        return this.price;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
