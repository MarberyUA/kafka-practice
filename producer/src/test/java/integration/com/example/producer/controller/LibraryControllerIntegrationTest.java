package com.example.producer.controller;

import com.example.producer.model.LibraryEvent;
import com.example.producer.model.LibraryEventType;
import com.example.producer.util.LibraryEventFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"})
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class LibraryControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createLibraryEvent() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LibraryEvent> httpEntity = new HttpEntity<>(LibraryEventFactory.createLibraryEvent(null, LibraryEventType.CREATE, null, "test", "author"),
                httpHeaders);

        ResponseEntity<LibraryEvent> responseEntity =  restTemplate.exchange("/v1/library/create", HttpMethod.POST, httpEntity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}