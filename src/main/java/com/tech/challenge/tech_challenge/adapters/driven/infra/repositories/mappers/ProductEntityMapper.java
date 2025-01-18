package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ProductEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public class ProductEntityMapper {
    public static Product toDomain(ProductEntity productEntity) {
        Product product = new Product();
        return product;
    }

    public static ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        return productEntity;
    }
}
