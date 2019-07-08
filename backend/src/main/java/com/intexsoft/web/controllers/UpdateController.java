package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.entityservice.AuthorService;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.PublisherService;
import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.pojo.mapping.AuthorImportMapper;
import com.intexsoft.service.importdata.pojo.mapping.BookImportMapper;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.PublisherDTO;
import com.intexsoft.web.dto.mapping.AuthorDTOMapper;
import com.intexsoft.web.dto.mapping.BookDTOMapper;
import com.intexsoft.web.dto.response.BookResponseDTO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${path.to.data.xml}")
    private String pathXML;
    @Value("${path.to.publishers.csv}")
    private String pathPublishersCSV;
    @Value("${path.to.authors.csv}")
    private String pathAuthorsCSV;
    @Value("${path.to.books.csv}")
    private String pathBooksCSV;
    @Value("${path.to.data.json}")
    private String pathJSON;

    public UpdateController(@Qualifier("importDataJAXBImpl") ImportData importData, Mapper mapper,
                            PublisherService publisherService, AuthorService authorService, BookService bookService,
                            BookDTOMapper bookDTOMapper, AuthorDTOMapper authorDTOMapper,
                            BookImportMapper bookImportMapper, AuthorImportMapper authorImportMapper) {
        this.importData = importData;
        this.mapper = mapper;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookDTOMapper = bookDTOMapper;
        this.authorDTOMapper = authorDTOMapper;
        this.bookImportMapper = bookImportMapper;
        this.authorImportMapper = authorImportMapper;
    }

    /**
     * update all data in database
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void updateDatabase(@RequestParam(defaultValue = "json", required = false) String fileType) {
        publisherService.saveBatch(getPublishers(fileType));
        authorService.saveBatch(getAuthors(fileType));
        bookService.saveBatch(getBooks(fileType));
    }

    /**
     * update Books in database
     * (be careful: Entity book depends on publishers and authors.
     * And that's why you must update publishers and authors at first)
     *
     * @return ResponseEntity with List of BookResponseDTO
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDTO>> updateDatabaseBooks(
            @RequestParam(defaultValue = "json", required = false) String fileType) {
        return ResponseEntity.ok(bookService.saveBatch(getBooks(fileType)).stream().
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
    public ResponseEntity<List<AuthorDTO>> updateDatabaseAuthors(
            @RequestParam(defaultValue = "json", required = false) String fileType) {
        return ResponseEntity.ok(authorService.saveBatch(getAuthors(fileType)).stream().
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
    public ResponseEntity<List<PublisherDTO>> updateDatabasePublishers(
            @RequestParam(defaultValue = "json", required = false) String fileType) {
        return ResponseEntity.ok(publisherService.saveBatch(getPublishers(fileType)).stream().
                map(publisher -> mapper.map(publisher, PublisherDTO.class)).collect(Collectors.toList()));
    }

    private List<Publisher> getPublishers(String fileType) {
        String path = getPathPublishersCSV(fileType);
        return importData.importPublishers(path).stream().map(publisherPOJO ->
                mapper.map(publisherPOJO, Publisher.class)).collect(Collectors.toList());
    }

    private List<Author> getAuthors(String fileType) {
        String path = getPathAuthorsCSV(fileType);
        return importData.importAuthors(path).stream().
                map(authorImportMapper::mapAuthorPOJOToAuthor).collect(Collectors.toList());
    }

    private List<Book> getBooks(String fileType) {
        String path = getPathBooksCSV(fileType);
        return importData.importBooks(path).stream().
                map(bookImportMapper::mapBookPOJOToBook).collect(Collectors.toList());
    }

    private String getPath(String fileType) {
        if ("xml".equals(fileType)) {
            return pathXML;
        }
        if ("json".equals(fileType)) {
            return pathJSON;
        }
        throw new IllegalArgumentException("This file type isn't supported");
    }

    private String getPathPublishersCSV(String fileType){
        if ("csv".equals(fileType)){
            return pathPublishersCSV;
        }else{
            return getPath(fileType);
        }
    }

    private String getPathAuthorsCSV(String fileType){
        if ("csv".equals(fileType)){
            return pathAuthorsCSV;
        }else{
            return getPath(fileType);
        }
    }

    private String getPathBooksCSV(String fileType){
        if ("csv".equals(fileType)){
            return pathBooksCSV;
        }else{
            return getPath(fileType);
        }
    }
}
