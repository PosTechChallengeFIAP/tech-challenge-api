package com.tech.challenge.tech_challenge.core.domain.useCases;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindProductsByCategoryUseCaseTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private FindProductsByCategoryUseCase findProductsByCategoryUseCase;

    @Test
    public void listByCategoryTest(){
        Product product1 = new Product();

        product1.setId(UUID.randomUUID());
        product1.setCategory(EProductCategory.SIDE_DISH);

        when(productRepository.findProductByCategory(EProductCategory.SIDE_DISH.ordinal())).thenReturn(List.of(product1));

        List<Product> products = findProductsByCategoryUseCase.execute(EProductCategory.SIDE_DISH);

        assertEquals(products.size(), 1);
        assertTrue(products.contains(product1));
        assertEquals(products.get(0).getCategory(), EProductCategory.SIDE_DISH);
    }
}
