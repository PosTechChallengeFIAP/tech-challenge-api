package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindClientByIdUseCaseTest {

    @Autowired
    private FindClientByIdUseCase findClientByIdUseCase;

    @MockBean
    private IClientRepository clientRepository;
    @Test
    public void getByIdTest_Success() throws ResourceNotFoundException {
        Client client = new Client();

        client.setId(UUID.randomUUID());

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client clientResult = findClientByIdUseCase.execute(client.getId());

        assertEquals(client, clientResult);
    }

    @Test
    public void getByIdTest_Exception() {
        UUID id = UUID.randomUUID();

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            findClientByIdUseCase.execute(id);
        });
    }
}
