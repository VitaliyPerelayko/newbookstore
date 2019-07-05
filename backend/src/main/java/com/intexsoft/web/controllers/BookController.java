package com.intexsoft.web.controllers;


import com.intexsoft.dao.model.Book;
import com.intexsoft.service.enyityservice.BookService;
import com.intexsoft.web.dto.mapping.BookDTOMapper;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import com.intexsoft.web.dto.response.BookResponseShortVersionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for entity Book.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final static Logger LOGGER = LogManager.getLogger(BookController.class);
    private final BookService bookService;
    private final BookDTOMapper bookDTOMapper;
    private final Mapper mapper;

    public BookController(BookService bookService, BookDTOMapper bookDTOMapper, Mapper mapper) {
        this.bookService = bookService;
        this.bookDTOMapper = bookDTOMapper;
        this.mapper = mapper;
    }

    /**
     * Gets all books from database.
     *
     * @return Response: BookResponseDTO ant http status
     */
    @GetMapping
    public ResponseEntity<List<BookResponseShortVersionDTO>> getAll() {
        List<Book> books = bookService.findAllSortByDate();
        List<BookResponseShortVersionDTO> bookDTOList = books.stream().map(book ->
                mapper.map(book, BookResponseShortVersionDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOList);
    }

    /**
     * Gets book by id
     *
     * @param id of book
     * @return Response: BookResponseDTO ant http status
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getOne(@PathVariable Long id) {
        BookResponseDTO bookResponseDTO = bookDTOMapper.mapBookToBookResponseDTO(bookService.findById(id));
        return ResponseEntity.ok(bookResponseDTO);
    }

    /**
     * Save book to database
     *
     * @param bookRequestDTO bookRequestDTO
     * @return Response: BookResponseDTO ant http status
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> save(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO bookResponseDTO = bookDTOMapper.mapBookToBookResponseDTO(bookService.
                save(bookDTOMapper.mapBookRequestDTOToBook(bookRequestDTO)));
        return ResponseEntity.ok(bookResponseDTO);
    }

    /**
     * Update book in database
     *
     * @param id             id of book which we want to update
     * @param bookRequestDTO bookRequestDTO
     * @return Response: BookResponseDTO ant http status
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        if (!id.equals(bookRequestDTO.getId())) {
            RuntimeException e = new RuntimeException("Id in URL path and id in request body must be the same");
            LOGGER.error(e);
            throw e;
        }
        BookResponseDTO bookResponseDTO = bookDTOMapper.mapBookToBookResponseDTO(bookService.
                update(bookDTOMapper.mapBookRequestDTOToBook(bookRequestDTO)));
        return ResponseEntity.ok(bookResponseDTO);
    }

    /**
     * Delete book from database and return OK Http status if there weren't any problem
     *
     * @param id id of book which we want to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}

