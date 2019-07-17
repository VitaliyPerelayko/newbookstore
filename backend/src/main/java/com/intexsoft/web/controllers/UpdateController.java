package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.importdata.pojo.mapping.PublisherImportMapper;
import com.intexsoft.service.entityservice.AuthorService;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.PublisherService;
import com.intexsoft.importdata.ImportData;
import com.intexsoft.importdata.pojo.mapping.AuthorImportMapper;
import com.intexsoft.importdata.pojo.mapping.BookImportMapper;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.PublisherDTO;
import com.intexsoft.web.dto.mapping.AuthorDTOMapper;
import com.intexsoft.web.dto.mapping.BookDTOMapper;
import com.intexsoft.web.dto.response.BookResponseDTO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for update data in database
 */
@RestController
@RequestMapping("/update")
public class UpdateController {

    private final ImportData importData;
    private final Mapper mapper;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BookDTOMapper bookDTOMapper;
    private final AuthorDTOMapper authorDTOMapper;
    private final BookImportMapper bookImportMapper;
    private final AuthorImportMapper authorImportMapper;
    private final PublisherImportMapper publisherImportMapper;

    public UpdateController(@Qualifier("importDataJAXBImpl") ImportData importData, Mapper mapper,
                            PublisherService publisherService, AuthorService authorService, BookService bookService,
                            BookDTOMapper bookDTOMapper, AuthorDTOMapper authorDTOMapper,
                            BookImportMapper bookImportMapper, AuthorImportMapper authorImportMapper,
                            PublisherImportMapper publisherImportMapper) {
        this.importData = importData;
        this.mapper = mapper;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookDTOMapper = bookDTOMapper;
        this.authorDTOMapper = authorDTOMapper;
        this.bookImportMapper = bookImportMapper;
        this.authorImportMapper = authorImportMapper;
        this.publisherImportMapper = publisherImportMapper;
    }

    /**
     * update all data in database
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void updateDatabase() {
        publisherService.saveBatch(getPublishers());
        authorService.saveBatch(getAuthors());
        bookService.saveBatch(getBooks());
    }

    /**
     * update Books in database
     * (be careful: Entity book depends on publishers and authors.
     * And that's why you must update publishers and authors at first)
     *
     * @return ResponseEntity with List of BookResponseDTO
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDTO>> updateDatabaseBooks() {
        return ResponseEntity.ok(bookService.saveBatch(getBooks()).stream().
                map(bookDTOMapper::mapBookToBookResponseDTO).collect(Collectors.toList()));
    }

    /**
     * update Authors in database
     * (be careful: Entity book depends on publishers and authors.
     * And that's why you must update publishers and authors at first)
     *
     * @return ResponseEntity with List of AuthorDTO
     */
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> updateDatabaseAuthors() {
        return ResponseEntity.ok(authorService.saveBatch(getAuthors()).stream().
                map(authorDTOMapper::mapAuthorToAuthorDTO).collect(Collectors.toList()));
    }

    /**
     * update Publishers in database
     * (be careful: Entity book depends on publishers and authors.
     * And that's why you must update publishers and authors at first)
     *
     * @return ResponseEntity with List of PublisherDTO
     */
    @GetMapping("/publishers")
    public ResponseEntity<List<PublisherDTO>> updateDatabasePublishers() {
        return ResponseEntity.ok(publisherService.saveBatch(getPublishers()).stream().
                map(publisher -> mapper.map(publisher, PublisherDTO.class)).collect(Collectors.toList()));
    }

    private List<Publisher> getPublishers() {
        return importData.importPublishers().stream().
                map(publisherImportMapper::mapPublisherPOJOToPublisher).collect(Collectors.toList());
    }

    private List<Author> getAuthors() {
        return importData.importAuthors().stream().
                map(authorImportMapper::mapAuthorPOJOToAuthor).collect(Collectors.toList());
    }

    private List<Book> getBooks() {
        return importData.importBooks().stream().
                map(bookImportMapper::mapBookPOJOToBook).collect(Collectors.toList());
    }
}
