package com.polarbookshop.catalog.domain;

import static java.lang.String.format;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super(format("Book with the isbn %s already exists", isbn));
    }
}
