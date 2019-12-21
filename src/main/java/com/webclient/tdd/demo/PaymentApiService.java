package com.webclient.tdd.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentApiService {

    final
    WebClient paymentApiWebClient;

    public PaymentApiService(WebClient paymentApiWebClient) {
        this.paymentApiWebClient = paymentApiWebClient;
    }

    @Bean
    public static WebClient getPaymentApiWebClient() {
        return WebClient.create("https://www.google.com");
    }

    public Mono<String> getAuthenticationToken() {
        return paymentApiWebClient
                .post()
                .uri("/api/authentication")
                .retrieve()
                .bodyToMono(String.class);
    }
}
