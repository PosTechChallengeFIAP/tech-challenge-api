package com.tech.challenge.tech_challenge.core.domain.useCases;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindProductByIdUseCaseTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private FindProductByIdUseCase findProductByIdUseCase;

    @Test
    public void getByIdTest_Success() throws Exception {
        Product product = new Product();

        product.setId(UUID.randomUUID());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product productResult = findProductByIdUseCase.execute(product.getId());

        assertEquals(product, productResult);
    }

    @Test
    public void getByIdTest_Exception() throws Exception {
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            findProductByIdUseCase.execute(id);
        });
    }
}
