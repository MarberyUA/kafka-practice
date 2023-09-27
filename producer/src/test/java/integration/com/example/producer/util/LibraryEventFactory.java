package com.example.producer.util;

import com.example.producer.model.Book;
import com.example.producer.model.LibraryEvent;
import com.example.producer.model.LibraryEventType;

public class LibraryEventFactory {
    private LibraryEventFactory() {

    }

    public static LibraryEvent createLibraryEventWithEmptyBook(Integer libraryEventId, LibraryEventType libraryEventType) {
        return createLibraryEvent(libraryEventId, libraryEventType, null, null, null);
    }

    public static LibraryEvent createLibraryEvent(Integer libraryEventId, LibraryEventType libraryEventType, Integer bookId, String bookName, String bookAuthor) {
        return new LibraryEvent(libraryEventId, libraryEventType, new Book(bookId, bookName, bookAuthor));
    }
}
