package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteProductUseCaseTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    public void deleteTest() throws Exception {
        Product product = mock(Product.class);

        doThrow(DataIntegrityViolationException.class).when(productRepository).delete(product);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        UsedProductCannotBeDeletedException ex = assertThrows(UsedProductCannotBeDeletedException.class, ()->{
            deleteProductUseCase.execute(UUID.randomUUID());
        });
    }
}
