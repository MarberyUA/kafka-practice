package com.example.producer.model;

public record LibraryEvent(Integer libraryEventId, LibraryEventType libraryEventType, Book book) {
}
