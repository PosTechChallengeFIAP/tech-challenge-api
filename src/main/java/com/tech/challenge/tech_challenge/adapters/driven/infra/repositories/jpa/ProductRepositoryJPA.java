package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepositoryJPA extends JpaRepository<ProductEntity, UUID> {
    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<ProductEntity> findProductByCategory(int categoryId);
}
