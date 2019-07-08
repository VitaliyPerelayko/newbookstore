package com.intexsoft.web.dto.mapping;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Category;
import com.intexsoft.service.entityservice.AuthorService;
import com.intexsoft.service.entityservice.PublisherService;
import com.intexsoft.web.dto.PublisherDTO;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.AuthorResponseInBookDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookDTOMapper {
    
    private final Mapper mapper;
    private final AuthorService authorService;
    private final PublisherService publisherService;


    public BookDTOMapper(Mapper mapper, AuthorService authorService, PublisherService publisherService) {
        this.mapper = mapper;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookResponseDTO mapBookToBookResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setName(book.getName());
        bookResponseDTO.setCode(book.getCode());
        bookResponseDTO.setCategory(book.getCategory().name());
        bookResponseDTO.setDescription(book.getDescription());
        bookResponseDTO.setPrice(book.getPrice());
        bookResponseDTO.setAuthors(book.getAuthors().stream().
                map(author -> mapper.map(author, AuthorResponseInBookDTO.class)).collect(Collectors.toList()));
        bookResponseDTO.setPublisher(mapper.map(book.getPublisher(), PublisherDTO.class));
        bookResponseDTO.setPublishDate(book.getPublishDate().toString());
        return bookResponseDTO;
    }

    public Book mapBookRequestDTOToBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setId(bookRequestDTO.getId());
        book.setName(bookRequestDTO.getName());
        book.setCode(bookRequestDTO.getCode());
        book.setCategory(Category.valueOf(bookRequestDTO.getCategory()));
        book.setDescription(bookRequestDTO.getDescription());
        book.setPrice(bookRequestDTO.getPrice());
        book.setAuthors(bookRequestDTO.getAuthors().stream().
                map(authorName -> authorService.findByName(authorName).
                        orElseThrow(() ->
                                new NoSuchElementException("author with the given name wasn't found in database"))).
                collect(Collectors.toSet()));
        book.setPublishDate(LocalDate.parse(bookRequestDTO.getPublishDate()));
        book.setPublisher(publisherService.findByName(bookRequestDTO.getPublisher()).orElseThrow(() ->
                        new NoSuchElementException("publisher with the given name wasn't found in database")));
        return book;
    }
}
