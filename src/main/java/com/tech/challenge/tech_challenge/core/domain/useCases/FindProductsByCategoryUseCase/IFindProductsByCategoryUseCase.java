package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsByCategoryUseCase;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface IFindProductsByCategoryUseCase {
    List<Product> execute(EProductCategory category);
}
