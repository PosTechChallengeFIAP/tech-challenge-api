package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepositoryJPA extends JpaRepository<Product, UUID> {
    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<Product> findProductByCategory(int categoryId);
}
