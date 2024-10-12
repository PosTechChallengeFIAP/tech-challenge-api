package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String id;
    private ArrayList<Product> products;
    private double price;

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
        this.price += product.getPrice();
    }

    public void removeProduct(Product product) {
        int idxToRemove = this.products.indexOf(product);
        this.products.remove(idxToRemove);
        this.price -= product.getPrice();
    }
}
