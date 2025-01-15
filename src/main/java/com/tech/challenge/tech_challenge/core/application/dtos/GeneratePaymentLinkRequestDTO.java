package com.tech.challenge.tech_challenge.core.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratePaymentLinkRequestDTO {
    private String itemId;
    private double amount;
    private String description;
    private int quantity;
}
