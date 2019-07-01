package com.intexsoft.web.controllers;

import com.intexsoft.service.AuthorService;
import com.intexsoft.service.BookService;
import com.intexsoft.service.PublisherService;
import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.PublisherDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import com.intexsoft.web.mapping.CustomMapping;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for update data in database
 */
@RestController
@RequestMapping("/update")
public class UpdateController {

    private final ImportData importData;
    private final CustomMapping customMapping;
    private final Mapper mapper;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final BookService bookService;

    public UpdateController(@Qualifier("importDataJAXBImpl") ImportData importData, CustomMapping customMapping,
                            Mapper mapper, PublisherService publisherService, AuthorService authorService,
                            BookService bookService) {
        this.importData = importData;
        this.customMapping = customMapping;
        this.mapper = mapper;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    /**
     * update all data in database
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void updateDatabase() {
        publisherService.saveAll(importData.importPublishers());
        authorService.saveAll(importData.importAuthors());
        bookService.saveAll(importData.importBooks());
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
        return ResponseEntity.ok(importData.importBooks().stream().
                map(customMapping::mapBookToBookResponseDTO).collect(Collectors.toList()));
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
        return ResponseEntity.ok(importData.importAuthors().stream().
                map(customMapping::mapAuthorToAuthorDTO).collect(Collectors.toList()));
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
        return ResponseEntity.ok(importData.importPublishers().stream().
                map(publisher -> mapper.map(publisher, PublisherDTO.class)).collect(Collectors.toList()));
    }
}
