package com.webclient.tdd.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import wiremock.org.apache.http.HttpHeaders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8080)
public class PaymentApiServiceTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PaymentApiService paymentApiService;

    @Test
    public void getAuthentication() throws JsonProcessingException {
        String token = "hashjhjsdsd";
        Token tokenObj = new Token(token);
        String tokenJson = this.objectMapper.writeValueAsString(tokenObj);

        WireMock.stubFor(WireMock
                .post(WireMock.urlEqualTo("/api/v1/authentication"))
                .willReturn(WireMock.aResponse()
                        .withBody(tokenJson)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                ));

        Flux<Token> tokenResponse = paymentApiService.getAuthenticationToken();

        StepVerifier.create(tokenResponse)
                .expectNext(new Token(token))
                .verifyComplete();

    }
}