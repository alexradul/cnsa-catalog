package com.polarbookshop.catalog.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("Cannot find book having isbn: " + isbn);
    }
}
