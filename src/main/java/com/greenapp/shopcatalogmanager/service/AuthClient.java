package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.dto.ClientMailDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.AUTH_URI;
import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.CLIENT_INFO;
import static com.greenapp.shopcatalogmanager.configuration.ShopConfig.getAuthHeaders;

@Service
@RequiredArgsConstructor
public class AuthClient {


    private static final Logger log = LoggerFactory.getLogger(AuthClient.class);
    private final RestTemplate restTemplate;


    public ClientMailDTO findAccountByClientId(Long clientId) {

        var uriParams = new HashMap<>();
        uriParams.put("clientId", clientId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTH_URI + CLIENT_INFO);
        HttpEntity<?> entity = new HttpEntity<>(getAuthHeaders());
        log.warn("Beginning request to get client: " + entity);

        ResponseEntity<ClientMailDTO> response = restTemplate.exchange(
                builder.buildAndExpand(uriParams).toUri(),
                HttpMethod.GET,
                entity,
                ClientMailDTO.class);

        return response.getBody();
    }
}
