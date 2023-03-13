package com.polarbookshop.catalog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Iterable<Book> viewBookList() {
        return repository.findAll();
    }

    public Book viewDetails(String isbn) {
        return repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (repository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return repository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        repository.deleteByIsbn(isbn);
    }

    public Book editDetails(String isbn, Book book) {
        return repository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            book.publisher(),
                            existingBook.createdAt(),
                            existingBook.lastModifiedAt(),
                            existingBook.version()
                    );
                    return repository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}
