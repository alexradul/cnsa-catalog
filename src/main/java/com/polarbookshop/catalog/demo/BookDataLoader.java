package com.polarbookshop.catalog.demo;

import com.polarbookshop.catalog.domain.Book;
import com.polarbookshop.catalog.domain.BookRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "catalog-service.setupTestData", havingValue = "true")
public class BookDataLoader {
    @Bean
    ApplicationRunner loadBookTestData(BookRepository repository) {
        return args -> {
            repository.save(Book.of("1234", "The Old Man and the Sea", "Ernest Hemingway", 100.0, "Macondo"));
            repository.save(Book.of("4321", "Catcher in the Ray", "J.D. Salinger", 100.0, "Macondo"));
        };
    }
}
