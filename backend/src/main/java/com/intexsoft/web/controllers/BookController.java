package com.intexsoft.web.controllers;


import com.intexsoft.dao.model.Book;
import com.intexsoft.service.BookService;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import com.intexsoft.web.dto.response.BookResponseShortVersionDTO;
import com.intexsoft.web.mapping.CustomMapping;
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

    private final BookService bookService;
    private final CustomMapping mapping;
    private final Mapper mapper;

    public BookController(BookService bookService, CustomMapping mapping, Mapper mapper) {
        this.bookService = bookService;
        this.mapping = mapping;
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
        BookResponseDTO bookResponseDTO = mapping.mapBookToBookResponseDTO(bookService.findById(id));
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
        BookResponseDTO bookResponseDTO = mapping.mapBookToBookResponseDTO(bookService.
                save(mapping.mapBookRequestDTOToBook(bookRequestDTO)));
        return ResponseEntity.ok(bookResponseDTO);
    }

    /**
     * Update book in database
     *
     * @param id id of book which we want to update
     * @param bookRequestDTO bookRequestDTO
     * @return Response: BookResponseDTO ant http status
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BookRequestDTO bookRequestDTO){
        if (!id.equals(bookRequestDTO.getId())){
            throw new RuntimeException("Id in URL path and id in request body must be the same");
        }
        BookResponseDTO bookResponseDTO = mapping.mapBookToBookResponseDTO(bookService.
                update(mapping.mapBookRequestDTOToBook(bookRequestDTO)));
        return ResponseEntity.ok(bookResponseDTO);
    }

    /**
     * Delete book from database and return OK Http status if there weren't any problem
     *
     * @param id id of book which we want to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        bookService.deleteById(id);
    }

}

