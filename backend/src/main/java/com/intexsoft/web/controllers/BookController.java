package com.intexsoft.web.controllers;


import com.intexsoft.dao.model.Book;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.web.dto.comparator.CompareByRating;
import com.intexsoft.web.dto.mapping.BookDTOMapper;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import com.intexsoft.web.dto.response.BookResponseShortVersionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for entity Book.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookDTOMapper bookDTOMapper;

    public BookController(BookService bookService, BookDTOMapper bookDTOMapper) {
        this.bookService = bookService;
        this.bookDTOMapper = bookDTOMapper;
    }

    /**
     * Gets all books from database sorted by publishDate.
     *
     * @return Response: BookResponseDTO ant http status
     */
    @GetMapping
    public ResponseEntity<List<BookResponseShortVersionDTO>> getAll() {
        List<Book> books = bookService.findAllSortByDate();
        List<BookResponseShortVersionDTO> bookDTOList = books.stream()
                .map(bookDTOMapper::mapBookToBookResponseShortVersionDTO).collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOList);
    }

    /**
     * Gets all books from database sorted by rating.
     * rating = average of book's reviews rating
     *
     * @return Response: BookResponseDTO ant http status
     */
    @GetMapping("/rating")
    public ResponseEntity<List<BookResponseShortVersionDTO>> getAllOrderedByRating() {
        List<Book> books = bookService.findAll();
        Stream<BookResponseShortVersionDTO> bookDTOList = books.stream()
                .map(bookDTOMapper::mapBookToBookResponseShortVersionDTO);
        return ResponseEntity.ok(bookDTOList.sorted(new CompareByRating()).collect(Collectors.toList()));
    }


    @GetMapping("/rating/{param}")
    public ResponseEntity<List<BookResponseShortVersionDTO>> getAllOrderedByRatingHigherThan(
            @PathVariable final Byte param) {
        List<Book> books = bookService.findAll();
        Stream<BookResponseShortVersionDTO> bookDTOList = books.stream()
                .map(bookDTOMapper::mapBookToBookResponseShortVersionDTO).filter(book -> {
                    Float rating = book.getRating();
                    return Objects.nonNull(rating) && rating > param;
                });
        return ResponseEntity.ok(bookDTOList.sorted(new CompareByRating()).collect(Collectors.toList()));
    }

    /**
     * Gets book with the highest rating
     *
     * @return Response: BookResponseDTO ant http status
     * @throws IllegalStateException if there aren't any books with review in database
     */
    @GetMapping("/rating-best")
    public ResponseEntity<BookResponseDTO> getOneWithTheHighestRating() {
        BookResponseDTO bookResponseDTO =
                bookDTOMapper.mapBookToBookResponseDTO(bookService.findByTheHighestRating());
        return ResponseEntity.ok(bookResponseDTO);
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
            throw new IllegalStateException("Id in URL path and id in request body must be the same");
        }
        BookResponseDTO bookResponseDTO = bookDTOMapper.mapBookToBookResponseDTO(bookService.
                update(bookDTOMapper.mapBookRequestDTOToBook(bookRequestDTO)));
        return ResponseEntity.ok(bookResponseDTO);
    }

    @PutMapping("/number/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void setNumberOfBooks(@PathVariable Long id, @RequestParam Short number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number of books must be positive");
        }
        bookService.setNumberOfBooks(id, number);
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

