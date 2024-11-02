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
    public Order addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.addItem(orderId, orderItem);
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
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId) throws Exception {
        return orderService.removeItem(orderId, itemId);
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
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.editItem(orderId, itemId, orderItem);
    }
}
