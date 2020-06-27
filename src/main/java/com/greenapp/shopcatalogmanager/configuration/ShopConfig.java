package com.greenapp.shopcatalogmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ShopConfig {

    public static final String BILLING_URI = " https://greenapp-reward-manager.herokuapp.com/reward-manager";
    public static final String WITHDRAW = "/writeoff";

    public static final String AUTH_URI = "https://greenapp-auth-service.herokuapp.com/auth";
    public static final String CLIENT_INFO = "/clients/{clientId}";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static HttpHeaders getAuthHeaders() {
        var headers = new HttpHeaders();
        headers.set("X-GREEN-APP-ID", "GREEN");
        return headers;
    }
}
