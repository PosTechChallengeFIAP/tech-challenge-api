package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Client", description = "Endpoint to manage clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    @Operation(summary = "Finds all clients", description = "Finds all clients",
                tags = {"Client"},
                responses ={
                        @ApiResponse(description = "Success", responseCode = "200",
                                content = {
                                    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Client.class)))
                                }),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                }
    )
    public List<Client> all(){
        return clientService.list();
    }

    @GetMapping("/client/{id}")
    public Client one(@PathVariable UUID id) throws Exception {
        return clientService.getById(id);
    }


    @PostMapping("/client")
    @Operation(summary = "Create Client", description = "Create Client",
            tags = {"Client"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Client.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Client newClient(@RequestBody Client client){
        return clientService.create(client);
    }
}

