package com.example.kafka.producer;

import com.example.kafka.model.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

@Slf4j
@Component
@AllArgsConstructor
public class LibraryEventsProducer {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.libraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        CompletableFuture<SendResult<Integer, String>> result =  kafkaTemplate.send("library-events", key, value);

        result.whenComplete(((sendResult, throwable) -> {
            if (throwable != null) {
                handleFailure(throwable, key, value);
                return;
            }
            handleSuccess(sendResult, key, value);
        }));
    }

    private void handleSuccess(SendResult<Integer, String> sendResult, Integer key, String value) {
        log.info(format("Producing an event was successful. Key: %s; Value: %s; Partition: %s;", key, value, sendResult.getRecordMetadata().partition()));
    }

    private void handleFailure(Throwable throwable, Integer key, String value) {
        log.error(format("Producing an event was failed. Reason: %s; Key: %s; Value: %s;", throwable.getMessage(), key, value));
    }
}
