package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.UUID;

public class ProductBuilder {
    private UUID id;
    private String name;
    private String description;
    private EProductCategory category;
    private Double price;
    private Boolean active;

    public ProductBuilder(){
        this.id = UUID.randomUUID();
        this.name = "Product 1";
        this.description = "Mock Product 1";
        this.category = EProductCategory.SNACK;
        this.price = 15.0;
        this.active = true;
    }

    public ProductBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ProductBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public ProductBuilder withCategory(EProductCategory category){
        this.category = category;
        return this;
    }

    public ProductBuilder withPrice(Double price){
        this.price = price;
        return this;
    }

    public ProductBuilder active(){
        this.active = true;
        return this;
    }

    public ProductBuilder inactive(){
        this.active = false;
        return this;
    }

    public Product build(){
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setActive(active);

        return product;
    }
}
