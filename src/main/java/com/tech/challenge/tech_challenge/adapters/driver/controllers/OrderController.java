package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.EMessageType;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.useCases.*;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddClientToOrderUseCase.AddClientToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreateOrderUseCase.CreateOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Order", description = "Endpoint to manage orders" )
public class OrderController {

    @Autowired
    private FindOrdersUseCase findOrdersUseCase;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private AddClientToOrderUseCase addClientToOrderUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @SuppressWarnings("rawtypes")
    @GetMapping("/order")
    @Operation(summary = "Finds all orders", description = "This endpoint is used to find all orders",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Order.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity all(){
        return ResponseEntity.status(HttpStatus.OK).body(findOrdersUseCase.execute());
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/order/{id}")
    @Operation(summary = "Finds order by Id", description = "This endpoint is used to find order by Id",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Order.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity one(@PathVariable UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(findOrderByIdUseCase.execute(id));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/order")
    @Operation(summary = "Create order", description = "This endpoint is used to create order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = {
                                    @Content(schema = @Schema(implementation = Order.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity create(@RequestBody Order order) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(createOrderUseCase.execute(order));
        }catch (ValidationException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/order/{orderId}/client/{clientId}")
    @Operation(summary = "Create order with client", description = "This endpoint is used to create order with client",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Order.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity addClient(@PathVariable(name = "orderId") UUID orderId, @PathVariable(name = "clientId") UUID clientId) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(addClientToOrderUseCase.execute(orderId, clientId));
        }catch (ValidationException | DataIntegrityViolationException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @SuppressWarnings("rawtypes")
    @PatchMapping("/order/{id}")
    @Operation(summary = "Update order", description = "This endpoint is used to update order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Order.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity updateOrder(@PathVariable UUID id, @RequestBody Order product) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(updateOrderUseCase.execute(id, product));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        } catch (ValidationException | IllegalAccessException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

}