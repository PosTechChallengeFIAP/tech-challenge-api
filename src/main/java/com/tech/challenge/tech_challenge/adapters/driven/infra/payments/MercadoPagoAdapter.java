package com.tech.challenge.tech_challenge.adapters.driven.infra.payments;
 
import java.util.List;

import org.springframework.stereotype.Component;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.tech.challenge.tech_challenge.adapters.driven.infra.payments.mappers.MercadoPagoAdapterMapper;
import com.tech.challenge.tech_challenge.core.application.dtos.GeneratePaymentLinkRequestDTO;
import com.tech.challenge.tech_challenge.core.application.ports.IPaymentGateway;

@Component
public class MercadoPagoAdapter implements IPaymentGateway {
    
    public MercadoPagoAdapter() {
        MercadoPagoConfig.setAccessToken(System.getenv("KEY_MERCADO_PAGO"));
    }
    
    @Override
    public String generatePaymentLink(List<GeneratePaymentLinkRequestDTO> items) {
        String link = null;

        List<PreferenceItemRequest> mercadoPagoItems = MercadoPagoAdapterMapper.toPreferenceItemRequest(items);

        PreferenceClient preferenceClient = new PreferenceClient();
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(mercadoPagoItems)
            .notificationUrl("http://localhost:8080/test")
            .build();

        try {
            Preference preference = preferenceClient.create(preferenceRequest);
            link = preference.getInitPoint();
        } catch (MPApiException ex) {
            System.out.printf(
                "MercadoPago Error. Status: %s, Content: %s%n",
                ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
        } catch (MPException ex) {
            ex.printStackTrace();
        }

        return link;
    }

    private PreferenceBackUrlsRequest getBackURLs() {
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            .success("https://www.teste/pending")
            .pending("https://www.teste/pending")
            .failure("https://www.teste/failure")
            .build();
        return backUrls;
    }
}
