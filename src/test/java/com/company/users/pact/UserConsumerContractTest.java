package com.company.users.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserConsumerContractTest {

    private static int getAvailablePort() {
        return new Random()
                .ints(6000, 9000)
                .filter(UserConsumerContractTest::isFree)
                .findFirst()
                .orElse(8080);
    }

    private static boolean isFree(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider", "127.0.0.1", getAvailablePort(), this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET")
                .uponReceiving("GET USER Request")
                .path("/users/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"id\":1,\"title\":\"DEFAULT\",\"firstname\":\"FirstName\",\"lastname\":\"LastName\",\"gender\":\"Male\",\"empid\":2332,\"address\":{\"street\":\"Street\",\"city\":\"sydney\",\"state\":\"State\",\"postcode\":\"postcode\"}}")
                .given("test POST")
                .uponReceiving("POST REQUEST")
                .method("POST")
                .headers(headers)
                .body("{\"title\":\"DEFAULT\",\"firstname\":\"FirstName\",\"lastname\":\"LastName\",\"gender\":\"Male\",\"empid\":2332,\"address\":{\"street\":\"Street\",\"city\":\"sydney\",\"state\":\"State\",\"postcode\":\"postcode\"}}")
                .path("/users")
                .willRespondWith()
                .status(201)
                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockProvider.getUrl() + "/users/1", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("firstname","FirstName","lastname","LastName");

        // and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{\"title\":\"DEFAULT\",\"firstname\":\"FirstName\",\"lastname\":\"LastName\",\"gender\":\"Male\",\"empid\":2332,\"address\":{\"street\":\"Street\",\"city\":\"sydney\",\"state\":\"State\",\"postcode\":\"postcode\"}}";

        // when
        ResponseEntity<String> postResponse = new RestTemplate().exchange(mockProvider.getUrl() + "/users", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
        assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
    }
}
