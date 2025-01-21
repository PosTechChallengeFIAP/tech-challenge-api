package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Endpoint to manage application health")
public class HealthController {

    @SuppressWarnings("rawtypes")
    @GetMapping("/health")
    @Operation(summary = "Check Health", description = "This endpoint is used to check the application health.",
            tags = {"Health"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
                            })
            }
    )
    public ResponseEntity isHealthy(){
            return ResponseEntity.status(HttpStatus.OK).body("System is Healthy");
    }
}