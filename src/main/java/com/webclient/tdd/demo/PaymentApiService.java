package com.webclient.tdd.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentApi {

    WebClient paymentApiWebClient;

    public String getAuthenticationToken() {
        return null;
    }
}
