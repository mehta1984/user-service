package com.company.users.integration;

import com.company.users.dto.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUserById() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic Z3Vlc3Q6Z3Vlc3QxMjM0");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String serviceURL = "http://localhost:" + port + "/users/1";
        ResponseEntity<String> response =restTemplate.exchange(serviceURL, HttpMethod.GET, entity, String.class);
    }


    @Test
    public void given_ReadOnlyRole_When_RequestToUpdate_Then_Forbidden() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        User user = easyRandom.nextObject(User.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic Z3Vlc3Q6Z3Vlc3QxMjM0");
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        String serviceURL = "http://localhost:" + port + "/users/1";
        ResponseEntity<String> response =restTemplate.exchange(serviceURL, HttpMethod.PUT, entity, String.class);



        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    public void when_UserCreateRequestReceived_ThenCreateUser() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        User user = easyRandom.nextObject(User.class);
        user.setId(null);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic YWRtaW46YWRtaW4xMjM0");
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        String serviceURL = "http://localhost:" + port + "/users";
        ResponseEntity<String> response =restTemplate.exchange(serviceURL, HttpMethod.POST, entity, String.class);
        assertEquals(200, response.getStatusCode().value());
    }
}


