package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.AddClientToOrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.AddPaymentToOrderService;
import com.tech.challenge.tech_challenge.core.domain.services.order.UpdateOrderPaymentService;

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
@Tag(name = "Order", description = "Endpoint to manage orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AddClientToOrderService addClientToOrderService;

    @Autowired
    AddPaymentToOrderService addPaymentToOrderService;

    @Autowired
    UpdateOrderPaymentService updateOrderPaymentService;

    @GetMapping("/order")
    @Operation(summary = "Finds all orders", description = "Finds all orders",
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
    @Operation(summary = "Finds a order", description = "Finds a order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
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
    @Operation(summary = "Creates a order", description = "Creates a order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order create(@RequestBody Order order) throws Exception {
        return orderService.create(order);
    }
    
    @PostMapping("/order/{orderId}/client/{clientId}")
    @Operation(summary = "Creates a order with client", description = "Creates a order with client",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order create(@PathVariable(name = "orderId") UUID orderId, @PathVariable(name = "clientId") UUID clientId) throws Exception {
        return addClientToOrderService.handle(orderId, clientId);
    }

    @PatchMapping("/order/{orderId}/addItem")
    @Operation(summary = "Add item to a order", description = "Add item to a order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order addItem(@PathVariable UUID orderId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.addItem(orderId, orderItem);
    }


    @PatchMapping("/order/{orderId}/removeItem/{itemId}")
    @Operation(summary = "Remove item from an order", description = "Remove item from an order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId) throws Exception {
        return orderService.removeItem(orderId, itemId);
    }

    @PatchMapping("/order/{orderId}/editItem/{itemId}")
    @Operation(summary = "Edit item from an order", description = "Edit item from an order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order addItem(@PathVariable UUID orderId, @PathVariable UUID itemId, @RequestBody OrderItem orderItem) throws Exception {
        return orderService.editItem(orderId, itemId, orderItem);
    }

    @PostMapping("/order/{orderId}/payment")
    @Operation(summary = "Add payment to an order", description = "Add payment to an order",
            tags = {"Order"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Order.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Order addPaymentoToOrder(@PathVariable UUID orderId) throws Exception {
        return addPaymentToOrderService.handle(orderId);
    }
/*
    @PatchMapping("/order/{orderId}/payment/{paymentId}")
    public Payment updateOrderPayment(@PathVariable UUID orderId, @PathVariable UUID paymentId, @RequestBody Payment payment) throws Exception {
        return updateOrderPaymentService.handle(orderId, paymentId, payment.getStatus());
    }
    */
}
