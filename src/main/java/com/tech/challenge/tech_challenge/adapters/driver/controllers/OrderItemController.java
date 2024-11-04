package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "OrderItem", description = "Endpoint to manage OrderItem" )
public class OrderItemController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/{orderId}/orderItem")
    @Operation(summary = "Add item in an order and creates OrderItem", description = "This endpoint is used to add item in an order and creates OrderItem",
              tags = {"OrderItem"},
              responses ={
                      @ApiResponse(description = "Success", responseCode = "200",
                              content = {
                                      @Content(schema = @Schema(implementation = Order.class))
                              }),
                      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                      @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
              }
      )
    public ResponseEntity addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) throws Exception {
        try{
            Order order = orderService.addItem(orderId, orderItem);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

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
    public ResponseEntity deleteItem(@PathVariable UUID orderId, @PathVariable UUID itemId) throws Exception {
        Order order = orderService.removeItem(orderId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

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
    public Order editItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.editItem(orderId, itemId, orderItem);
    }
}
