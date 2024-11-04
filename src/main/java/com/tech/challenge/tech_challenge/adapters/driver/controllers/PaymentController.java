package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.EMessageType;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.extended.OrderPaymentService;
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

import java.util.UUID;

@RestController
@Tag(name = "Payment", description = "Endpoint to manage payment" )
public class PaymentController {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @PostMapping("/order/{orderId}/payment")
    @Operation(summary = "Add new payment to an order", description = "This endpoint is used to add a new payment to an order",
            tags = {"Payment"},
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
    public ResponseEntity addPaymentoToOrder(@PathVariable UUID orderId){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderPaymentService.addPaymentToOrder(orderId));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        } catch (ValidationException | DataIntegrityViolationException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage(ex.getMessage()));
        }
    }

    @PatchMapping("/order/{orderId}/payment/{paymentId}")
    @Operation(summary = "Update payment from an order", description = "This endpoint is used to update payment from an order",
            tags = {"Payment"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Payment.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity updateOrderPayment(@PathVariable UUID orderId, @PathVariable UUID paymentId, @RequestBody Payment payment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderPaymentService.updateOrderPayment(orderId, paymentId, payment.getStatus()));
        } catch (ResourceNotFoundException ex) {
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
