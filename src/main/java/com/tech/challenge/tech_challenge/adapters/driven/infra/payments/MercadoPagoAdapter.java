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
        MercadoPagoConfig.setAccessToken(System.getenv("APP_USR-2294323759964042-011214-fe2787640823fb66d57ce7c27dde60d6-2202504787"));
    }
    
    @Override
    public String generatePaymentLink(List<GeneratePaymentLinkRequestDTO> items, String callbackURL) {
        String link = null;

        List<PreferenceItemRequest> mercadoPagoItems = MercadoPagoAdapterMapper.toPreferenceItemRequest(items);

        PreferenceClient preferenceClient = new PreferenceClient();
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(mercadoPagoItems)
            .backUrls(this.getBackURLs(callbackURL))
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

    private PreferenceBackUrlsRequest getBackURLs(String defaultURL) {
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            .success(String.format("%s?status=success", defaultURL))
            .pending(String.format("%s?status=pending", defaultURL))
            .failure(String.format("%s?status=failure", defaultURL))
            .build();
        return backUrls;
    }
}
