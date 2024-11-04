package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderClientService;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderPaymentService;

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
@Tag(name = "Order", description = "Endpoint to manage orders" )
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderClientService orderClientService;
  
    @Autowired
    OrderPaymentService orderPaymentService;

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
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<Order> all(){
        return orderService.list();
    }

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
    public Order one(@PathVariable UUID id) throws Exception {
        return orderService.getById(id);
    }

    @PostMapping("/order")
    @Operation(summary = "Create order", description = "This endpoint is used to create order",
            tags = {"Order"},
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
    public Order create(@RequestBody Order order) throws Exception {
        return orderService.create(order);
    }
    
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
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order create(@PathVariable(name = "orderId") UUID orderId, @PathVariable(name = "clientId") UUID clientId) throws Exception {
        return orderClientService.addClientToOrder(orderId, clientId);
    }
}
