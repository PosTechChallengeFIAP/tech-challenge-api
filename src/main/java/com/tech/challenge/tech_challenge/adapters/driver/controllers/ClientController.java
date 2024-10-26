package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public List<Client> all(){
        return clientService.list();
    }
   /*
    @GetMapping("/client/{id}")
    public Client one(@PathVariable UUID id) throws Exception {
        return clientService.getById(id);
    }
    */

    @GetMapping("/client/{cpf}")
    public Client getClientByCpf(@PathVariable String cpf) throws Exception {
        return clientService.getByCpf(cpf);
    }

    @PostMapping("/client")
    public Client newClient(@RequestBody Client client){
        return clientService.create(client);
    }
}

