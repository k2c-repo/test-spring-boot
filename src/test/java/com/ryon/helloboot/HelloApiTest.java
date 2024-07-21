package com.ryon.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class HelloApiTest {
    @Test
    void helloApi(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> res =
                restTemplate.getForEntity("http://localhost:8080/v1/hello?name={name}", String.class, "Spring");

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
        Assertions.assertThat(res.getBody()).isEqualTo("*Hello Spring*");
    }

    @Test
    void failHelloApi(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> res =
                restTemplate.getForEntity("http://localhost:8080/v1/hello?name=", String.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
