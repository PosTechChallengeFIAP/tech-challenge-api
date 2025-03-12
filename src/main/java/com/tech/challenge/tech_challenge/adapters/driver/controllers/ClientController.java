package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.EMessageType;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreateClientUseCase.ICreateClientUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByCpfUseCase.IFindClientByCpfUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase.IFindClientByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindClientsUseCase.IFindClientsUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Client", description = "Endpoint to manage client")
public class ClientController {

    @Autowired
    private IFindClientsUseCase findClientsUseCase;

    @Autowired
    private IFindClientByIdUseCase findClientByIdUseCase;

    @Autowired
    private IFindClientByCpfUseCase findClientByCpfUseCase;

    @SuppressWarnings("rawtypes")
    @GetMapping("/client")
    @Operation(summary = "Finds clients", description = "This endpoint is used to find client. " +
            "If the request has a CPF it returns the Client with the same CPF value. Otherwise it returns all clients",
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
    public ResponseEntity all(@RequestParam(required = false) String cpf){
        if(StringUtils.isEmpty(cpf)) {
            List<Client> clients = findClientsUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(clients);
        }
        else {
            try {
                Client client = findClientByCpfUseCase.execute(cpf);
                return ResponseEntity.status(HttpStatus.OK).body(List.of(client));
            }catch (ResourceNotFoundException ex){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
            }catch (ValidationException ex){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/client/{id}")
    @Operation(summary = "Finds client by Id", description = "This endpoint is used to find client by Id",
            tags = {"Client"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Client.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity one(@PathVariable UUID id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(findClientByIdUseCase.execute(id));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }
}

