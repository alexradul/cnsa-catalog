package com.polarbookshop.catalog.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

public record Book(
        @Id
        Long id,
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
        BigDecimal price,

        String publisher,
        @CreatedDate
        @Column("created_date")
        Instant createdAt,
        @LastModifiedDate
        @Column("last_modified_date")
        Instant lastModifiedAt,
        @Version
        int version,

        @CreatedBy
        String createdBy,

        @LastModifiedBy
        String lastModifiedBy) {

        public static Book of(String isbn, String title, String author, double price, String publisher) {
                return of(isbn, title, author, BigDecimal.valueOf(price), publisher);
        }

        public static Book of(String isbn, String title, String author, BigDecimal price, String publisher) {
                return new Book(null, isbn, title, author, price, publisher, null, null, 0, null, null);
        }
}
