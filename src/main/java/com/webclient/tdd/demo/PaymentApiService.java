package com.webclient.tdd.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class PaymentApiService {

    final
    WebClient paymentApiWebClient;

    public PaymentApiService(WebClient paymentApiWebClient) {
        this.paymentApiWebClient = paymentApiWebClient;
    }

    @Bean
    public static WebClient getPaymentApiWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080/api/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Flux<Token> getAuthenticationToken() {
        return paymentApiWebClient
                .post()
                .uri("/authentication")
                .retrieve()
                .bodyToFlux(Token.class);
    }

}