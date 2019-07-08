package com.intexsoft.service.importdata.pojo.mapping;

import com.intexsoft.dao.model.Book;
import com.intexsoft.service.entityservice.AuthorService;
import com.intexsoft.service.entityservice.PublisherService;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookImportMapper {

    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookImportMapper(AuthorService authorService, PublisherService publisherService) {
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    public Book mapBookPOJOToBook(BookPOJO bookPOJO) {
        Book book = new Book();
        book.setName(bookPOJO.getName());
        book.setCode(bookPOJO.getUniqueValue());
        book.setCategory(bookPOJO.getCategory());
        book.setDescription(bookPOJO.getDescription());
        book.setPrice(bookPOJO.getPrice());
        book.setAuthors(bookPOJO.getAuthors().stream().
                map(authorName -> authorService.findByName(authorName).
                        orElseThrow(() ->
                                new NoSuchElementException("author with the given name wasn't found in database"))).
                collect(Collectors.toSet()));
        book.setPublishDate(bookPOJO.getPublishDate());
        book.setPublisher(publisherService.findByName(bookPOJO.getPublisher()).orElseThrow(() ->
                new NoSuchElementException("publisher with the given name wasn't found in database")));
        return book;
    }
}
