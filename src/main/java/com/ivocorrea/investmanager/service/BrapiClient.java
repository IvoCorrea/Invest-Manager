package com.ivocorrea.investmanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BrapiClient {

    private final WebClient webClient;
    private final String apiToken;

    public BrapiClient(@Value("${brapi.token}") String apiToken,
                       @Value("${brapi.url}") String apiUrl) {

        this.apiToken = apiToken;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

    public String getPrice(String ticker) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/quote/{ticker}")
                        .queryParam("token", apiToken)
                        .build(ticker))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
