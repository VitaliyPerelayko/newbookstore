package com.intexsoft.web.mapping;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;

public interface CustomMapping {
    BookResponseDTO mapBookToBookResponseDTO(Book book);

    Book mapBookRequestDTOToBook(BookRequestDTO bookRequestDTO);

    Book mapBookPOJOToBook(BookPOJO bookPOJO);

    Author mapAuthorDTOToAuthor(AuthorDTO authorDTO);

    AuthorDTO  mapAuthorToAuthorDTO(Author author);

    Author mapAuthorPOJOToAuthor(AuthorPOJO authorPOJO);
}
