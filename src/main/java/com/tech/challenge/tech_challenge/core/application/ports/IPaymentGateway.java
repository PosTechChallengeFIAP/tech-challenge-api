package com.tech.challenge.tech_challenge.core.application.ports;

import java.util.List;

import com.tech.challenge.tech_challenge.core.application.dtos.GeneratePaymentLinkRequestDTO;

public interface IPaymentGateway {
    String generatePaymentLink(List<GeneratePaymentLinkRequestDTO> items, String callbackURL);
}