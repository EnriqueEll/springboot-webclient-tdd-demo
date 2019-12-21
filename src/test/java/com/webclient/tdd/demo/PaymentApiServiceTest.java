package com.webclient.tdd.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PaymentApiServiceTest {

    @Autowired
    PaymentApiService paymentApiService;

    @MockBean
    WebClient paymentApiWebClient;

    @Mock
    WebClient.RequestBodySpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUri;

    @Mock
    WebClient.ResponseSpec responseSpekMock;

    @Test
    public void shouldGetAuthenticationToken() {
        String tokenExpected = "jhaskjdhlas=sadklasd";
        when(paymentApiWebClient.post())
                .thenReturn(requestBodyUri);
        when(requestBodyUri.uri("/api/authentication")).
                thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve())
                .thenReturn(responseSpekMock);
        when(responseSpekMock.bodyToMono(String.class))
                .thenReturn(Mono.just(tokenExpected));

        Mono<String> token = paymentApiService.getAuthenticationToken();

        assertEquals(tokenExpected, token.block());
    }

    
}
