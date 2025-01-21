package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.ClientBuilder;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientsUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreateClientUseCase.CreateClientUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByCpfUseCase.FindClientByCpfUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase.FindClientByIdUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("rawtypes")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private FindClientsUseCase findClientsUseCase;

    @MockBean
    private FindClientByIdUseCase findClientByIdUseCase;

    @MockBean
    private FindClientByCpfUseCase findClientByCpfUseCase;

    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
    }

    @Test
    void listTest_Success() {
        Client client1 = new ClientBuilder().build();
        Client client2 = new ClientBuilder().build();

        when(findClientsUseCase.execute()).thenReturn(List.of(client2,client1));

        ResponseEntity<List> resultList  = this.restTemplate.getForEntity(getFullUrl("/client"),
                List.class);

        List<Client> clients = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            clients.add(mapper.convertValue(object, Client.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(clients.size(),2);
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }

    @Test
    void listByCPFTest_Success() throws ResourceNotFoundException, ValidationException {
        Client client1 = new ClientBuilder().build();

        when(findClientByCpfUseCase.execute(client1.getCpf())).thenReturn(client1);

        ResponseEntity<List> resultList = this.restTemplate.getForEntity(getFullUrl("/client?cpf=" + client1.getCpf()),
                List.class);

        List<Client> clients = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            clients.add(mapper.convertValue(object, Client.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(clients.size(),1);
        assertTrue(clients.contains(client1));
    }

    @Test
    void listByCPFTest_NotFound() throws ResourceNotFoundException, ValidationException {
        String cpf = "324.553.080-30";
        when(findClientByCpfUseCase.execute(cpf)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> resultList = this.restTemplate.getForEntity(getFullUrl("/client?cpf=" + cpf),
                MessageResponse.class);

        assertEquals(resultList.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void listByCPFTest_BadRequest() throws ValidationException, ResourceNotFoundException {
        String cpf = "324.553.080-3";
        when(findClientByCpfUseCase.execute(cpf)).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> resultList = this.restTemplate.getForEntity(getFullUrl("/client?cpf=" + cpf),
                MessageResponse.class);

        assertEquals(resultList.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getByIdTest_Success() throws ResourceNotFoundException {
        Client client = new ClientBuilder().build();

        when(findClientByIdUseCase.execute(client.getId())).thenReturn(client);

        ResponseEntity<Client> result = this.restTemplate.getForEntity(getFullUrl("/client/" + client.getId()),
                Client.class);

        Client clientResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(client,clientResult);
    }

    @Test
    void getByIdTest_NotFound() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();

        when(findClientByIdUseCase.execute(id)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/client/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByIdTest_BadRequest(){
        String id = "47568437";

        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/client/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createTest_Success() throws ValidationException {
        Client client = new ClientBuilder().withId(null).build();
        Client createdClient = new ClientBuilder().build();

        when(createClientUseCase.execute(any())).thenReturn(createdClient);

        ResponseEntity<Client> result = this.restTemplate.postForEntity(
                getFullUrl("/client"),
                client,
                Client.class
        );

        Client clientResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(createdClient, clientResult);
    }

    @Test
    void createTest_BadRequest() throws ValidationException {
        Client client = new ClientBuilder().withId(null).build();

        when(createClientUseCase.execute(any())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl("/client"),
                client,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
