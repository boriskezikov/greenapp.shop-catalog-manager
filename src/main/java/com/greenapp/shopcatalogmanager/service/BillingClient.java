package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.configuration.BillingFailedException;
import com.greenapp.shopcatalogmanager.dto.WriteOffDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.BILLING_URI;
import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.WITHDRAW;
import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.getAuthHeaders;

@Service
@RequiredArgsConstructor
public class BillingClient {

    private static final Logger log = LoggerFactory.getLogger(AuthClient.class);
    private final RestTemplate restTemplate;


    public void withdrawFunds(WriteOffDTO writeOffDTO) {

        var builder = UriComponentsBuilder.fromHttpUrl(BILLING_URI + WITHDRAW);
        var requestEntity = new HttpEntity<>(writeOffDTO, getAuthHeaders());

        log.warn("Requesting to withdraw funds: {} from client: {} ", writeOffDTO.amount, writeOffDTO.clientId);

        var response = restTemplate.postForEntity(
                builder.toUriString(),
                requestEntity,
                Void.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BillingFailedException();
        }
    }
}
