package com.example.producer.controller;

import com.example.producer.model.LibraryEvent;
import com.example.producer.model.LibraryEventType;
import com.example.producer.producer.LibraryEventsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/library")
@AllArgsConstructor
public class LibraryController {

    private final LibraryEventsProducer libraryEventsProducer;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryEvent> createLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException {
        log.info("libraryEvent : {}", libraryEvent);

        libraryEventsProducer.sendEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException {
        log.info("libraryEvent : {}", libraryEvent);

        ResponseEntity<String> BAD_REQUEST = validateUpdateLibraryEvent(libraryEvent);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        libraryEventsProducer.sendEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    private static ResponseEntity<String> validateUpdateLibraryEvent(LibraryEvent libraryEvent) {
        if (libraryEvent.libraryEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event can't be persist without id.");
        }
        if (libraryEvent.libraryEventType() != LibraryEventType.UPDATE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only UPDATE event is supported.");
        }
        return null;
    }
}
