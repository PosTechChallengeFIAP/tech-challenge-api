package com.tech.challenge.tech_challenge.core.domain.useCases;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;
import com.tech.challenge.tech_challenge.core.domain.services.generic.Patcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateProductUseCaseTest {
    @MockBean
    private IProductRepository productRepository;

    @Autowired
    private UpdateProductUseCase updateProductUseCase;

    @MockBean
    private Patcher<Product> productPatcher;

    @Test
    public void updateTest() throws Exception {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(productPatcher.execute(product1, product2)).thenReturn(product1);
        when(productRepository.save(product1)).thenReturn(product1);

        Product productResult = updateProductUseCase.execute(UUID.randomUUID(), product2);

        assertEquals(product1, productResult);
    }
}
