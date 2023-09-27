package com.example.producer.controller;

import com.example.producer.model.LibraryEvent;
import com.example.producer.model.LibraryEventType;
import com.example.producer.producer.LibraryEventsProducer;
import com.example.producer.util.LibraryEventFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LibraryEventsProducer libraryEventsProducer;

    @Test
    void createLibraryEvent() throws Exception {
        String json = objectMapper.writeValueAsString(LibraryEventFactory.createLibraryEvent(1, LibraryEventType.CREATE, 1, "test", "test"));
        doNothing().when(libraryEventsProducer).sendEvent(isA(LibraryEvent.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/library/create").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createInvalidEvent() throws Exception {
        String json = objectMapper.writeValueAsString(LibraryEventFactory.createLibraryEventWithEmptyBook(1, LibraryEventType.CREATE));
        doNothing().when(libraryEventsProducer).sendEvent(isA(LibraryEvent.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/library/create").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}