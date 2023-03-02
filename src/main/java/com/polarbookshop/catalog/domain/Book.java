package com.polarbookshop.catalog.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record Book(
        @NotBlank(
                message = "The book ISBN must be defined.")
        @Pattern(
                regexp = "^(\\d{10}|\\d{13})$",
                message = "Invalid ISBN format."
        )
        String isbn,
        @NotBlank(
                message = "The book title must be defined.")
        String title,
        @NotBlank(
                message = "The book author must be defined.")
        String author,
        @NotNull(
                message = "The book price must be defined.")
        @Positive(
                message = "The price cannot be negative value."
        )
        BigDecimal price) {
}
