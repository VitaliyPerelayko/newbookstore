package com.intexsoft.web.mapping.impl;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.service.AuthorService;
import com.intexsoft.service.PublisherService;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.PublisherDTO;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.AuthorResponseInBookDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import com.intexsoft.web.mapping.CustomMapping;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class CustomMappingImpl implements CustomMapping {

    private final Mapper mapper;
    private final AuthorService authorService;
    private final PublisherService publisherService;


    public CustomMappingImpl(Mapper mapper, AuthorService authorService, PublisherService publisherService) {
        this.mapper = mapper;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Override
    public BookResponseDTO mapBookToBookResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = mapper.map(book, BookResponseDTO.class);
        bookResponseDTO.setAuthors(book.getAuthors().stream().
                map(author -> mapper.map(author, AuthorResponseInBookDTO.class)).collect(Collectors.toList()));
        bookResponseDTO.setPublisher(mapper.map(book.getPublisher(), PublisherDTO.class));
        bookResponseDTO.setPublishDate(book.getPublishDate().toString());
        return bookResponseDTO;
    }

    @Override
    public Book mapBookRequestDTOToBook(BookRequestDTO bookRequestDTO) {
        Book book = mapper.map(bookRequestDTO, Book.class);
        book.setAuthors(bookRequestDTO.getAuthorsId().stream().
                map(authorService::findById).collect(Collectors.toSet()));
        book.setPublishDate(LocalDate.parse(bookRequestDTO.getPublishDate()));
        book.setPublisher(publisherService.findById(bookRequestDTO.getPublisherId()));
        return book;
    }

    @Override
    public Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {
        Author author = mapper.map(authorDTO, Author.class);
        author.setBirthDate(LocalDate.parse(authorDTO.getBirthDate()));
        return author;
    }

    @Override
    public AuthorDTO  mapAuthorToAuthorDTO(Author author) {
        AuthorDTO authorDTO = mapper.map(author, AuthorDTO.class);
        authorDTO.setBirthDate(author.getBirthDate().toString());
        return authorDTO;
    }



}
