package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ProductEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public class ProductEntityMapper {
    public static Product toDomain(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setPrice(productEntity.getPrice());
        product.setActive(productEntity.getActive());
        return product;
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        productEntity.setActive(product.getActive());
        return productEntity;
    }
}
