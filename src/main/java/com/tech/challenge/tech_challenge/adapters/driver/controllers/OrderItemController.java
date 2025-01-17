package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.EMessageType;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddItemToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.EditItemToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.RemoveItemToOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@Tag(name = "OrderItem", description = "Endpoint to manage OrderItem" )
public class OrderItemController {

    @Autowired
    private AddItemToOrderUseCase addItemToOrderUseCase;

    @Autowired
    private RemoveItemToOrderUseCase removeItemToOrderUseCase;

    @Autowired
    private EditItemToOrderUseCase editItemToOrderUseCase;

    @SuppressWarnings("rawtypes")
    @PostMapping("/order/{orderId}/orderItem")
    @Operation(summary = "Add item in an order and creates OrderItem", description = "This endpoint is used to add item in an order and creates OrderItem",
            tags = {"OrderItem"},
            responses ={
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = {
                                    @Content(schema = @Schema(implementation = Order.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(addItemToOrderUseCase.execute(orderId, orderItem));
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
    @DeleteMapping("/order/{orderId}/orderItem/{itemId}")
    @Operation(summary = "Delete item from Order", description = "This endpoint is used to Delete item from Order",
            tags = {"OrderItem"},
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
    public ResponseEntity removeItem(@PathVariable UUID orderId, @PathVariable UUID itemId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(removeItemToOrderUseCase.execute(orderId, itemId));
        }catch (ResourceNotFoundException | NoSuchElementException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @SuppressWarnings("rawtypes")
    @PatchMapping("/order/{orderId}/orderItem/{itemId}")
    @Operation(summary = "Edit item from Order", description = "This endpoint is used to edit item from Order",
            tags = {"OrderItem"},
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
    public ResponseEntity editItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(editItemToOrderUseCase.execute(orderId, itemId, orderItem));
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        } catch (ValidationException | DataIntegrityViolationException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }
}
