package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCase {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private FindProductByIdUseCase findProductByIdUseCase;

    public void execute(UUID id) throws ResourceNotFoundException {
        Product product = findProductByIdUseCase.execute(id);

        try {
            productRepository.delete(product);
        }catch (DataIntegrityViolationException ex){
            throw new UsedProductCannotBeDeletedException(id.toString(), ex);
        }
    }
}
