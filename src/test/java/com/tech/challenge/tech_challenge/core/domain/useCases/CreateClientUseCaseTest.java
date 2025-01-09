package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ClientRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateClientUseCaseTest {

    @Autowired
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private ClientRepository clientRepository;
    @Test
    public void createTest_Success() throws ValidationException {
        Client client = mock(Client.class);

        when(clientRepository.save(client)).thenReturn(client);

        Client clientResult = createClientUseCase.execute(client);

        assertEquals(client, clientResult);
    }

    @Test
    public void createTest_Exception() throws ValidationException {
        Client client = mock(Client.class);

        when(clientRepository.save(client)).thenReturn(client);
        doThrow(ValidationException.class).doNothing().when(client).validate();

        assertThrows(ValidationException.class, ()->{
            createClientUseCase.execute(client);
        });
    }
}
