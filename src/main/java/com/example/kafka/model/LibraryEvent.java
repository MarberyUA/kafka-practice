package com.example.kafka.model;

public record LibraryEvent(Integer libraryEventId, LibraryEventType libraryEventType, Book book) {
}
