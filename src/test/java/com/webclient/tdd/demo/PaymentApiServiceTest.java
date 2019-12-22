package com.webclient.tdd.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PaymentApiServiceTest {

    @Autowired
    PaymentApiService paymentApiService;

    @MockBean
    WebClient paymentApiWebClient;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUri;

    @Mock
    WebClient.ResponseSpec responseSpekMock;

    @Mock
    RequestHeadersSpec requestHeaderSpec;

    @Mock
    WebClient.ResponseSpec requestSpec;

    private String buildAuthenticationTokenArrange() {
        String tokenExpected = "jhaskjdhlas=sadklasd";
        when(paymentApiWebClient.post())
                .thenReturn(requestBodyUri);
        when(requestBodyUri.uri("/api/authentication")).
                thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve())
                .thenReturn(responseSpekMock);
        when(responseSpekMock.bodyToMono(String.class))
                .thenReturn(Mono.just(tokenExpected));
        return tokenExpected;
    }

    @Test
    public void shouldGetAuthenticationToken() {
        String tokenExpected = buildAuthenticationTokenArrange();

        Mono<String> token = paymentApiService.getAuthenticationToken();

        assertEquals(tokenExpected, token.block());
    }

    @Test
    public void shouldPostPaymentApprove() {
        String uri = "/api/payment";
        Payment payment = new Payment(122.00D);
        buildPostPaymentApprovePaymentArrenge(uri, payment);

        ResponseEntity<Void> entity = paymentApiService.postPayment(payment);

        verify(paymentApiWebClient, times(1)).post();
        verify(paymentApiWebClient.post(), times(1)).uri(uri);
        RequestHeadersSpec<?> body = paymentApiWebClient.post().uri(uri).body(payment, Payment.class);
        verify(body, times(1)).retrieve();
        verify(body.retrieve(), times(1)).toBodilessEntity();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    private void buildPostPaymentApprovePaymentArrenge(String uri, Payment payment) {
        when(paymentApiWebClient.post()).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(uri)).thenReturn(requestBodyUri);
        when(requestBodyUri.body(payment, Payment.class))
                .thenReturn(requestHeaderSpec);
        when(requestHeaderSpec.retrieve()).thenReturn(requestSpec);
        when(requestSpec.toBodilessEntity()).thenReturn(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
    }
}
