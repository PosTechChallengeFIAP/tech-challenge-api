package com.tech.challenge.tech_challenge.core.domain.entities;

import jakarta.persistence.Column;

import java.util.UUID;

public class ClientBuilder {
    private UUID id;
    private String name;
    private String cpf;
    private String email;

    public ClientBuilder(){
        id = UUID.randomUUID();
        name = "John Doe";
        cpf = "927.206.170-91";
        email = "johndoe@example.com";
    }

    public ClientBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public ClientBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ClientBuilder withCPF(String cpf){
        this.cpf = cpf;
        return this;
    }

    public ClientBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public Client build(){
        Client client = new Client();
        client.setId(this.id);
        client.setCpf(this.cpf);
        client.setName(this.name);
        client.setEmail(this.email);

        return client;
    }
}
