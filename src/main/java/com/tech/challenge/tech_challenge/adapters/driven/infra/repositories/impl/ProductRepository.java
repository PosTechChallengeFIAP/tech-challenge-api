package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.ProductRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.ProductEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ProductEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements IProductRepository {
    @Autowired
    ProductRepositoryJPA productRepositoryJPA;

    @Override
    public Product save(Product productToSave) {
        ProductEntity productEntityToSave = toEntity(productToSave);
        ProductEntity savedProductEntity = productRepositoryJPA.save(productEntityToSave);
        Product product = toDomain(savedProductEntity);
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productsEntity = productRepositoryJPA.findAll();
        List<Product> products = toDomain(productsEntity);
        return products;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<Product> product = productRepositoryJPA.findById(id).map(productEntity -> toDomain(productEntity));
        return product;
    }

    @Override
    public List<Product> findProductByCategory(int idCategory) {
        List<ProductEntity> productsEntity = productRepositoryJPA.findProductByCategory(idCategory);
        List<Product> products = toDomain(productsEntity);
        return products;
    }

    @Override
    public void delete(Product product) {
        ProductEntity productEntity = toEntity(product);
        productRepositoryJPA.delete(productEntity);
    }

    private Product toDomain(ProductEntity productEntity) {
        return ProductEntityMapper.toDomain(productEntity);
    }

    private List<Product> toDomain(List<ProductEntity> productsEntity) {
        List<Product> products = new ArrayList<>();

        productsEntity.forEach(productEntity -> {
            Product product = toDomain(productEntity);
            products.add(product);
        });

        return products;
    }

    private ProductEntity toEntity(Product product) {
        return ProductEntityMapper.toEntity(product);
    }
}
