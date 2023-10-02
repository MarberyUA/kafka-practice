package com.example.producer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Book(@NotNull Integer bookId, @NotNull @NotBlank String bookName, String bookAuthor) {
}
