package com.intexsoft.web.dto.mapping;

import com.intexsoft.dao.model.Author;
import com.intexsoft.web.dto.AuthorDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthorDTOMapper {

    public Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        author.setBio(authorDTO.getBio());
        author.setBirthDate(LocalDate.parse(authorDTO.getBirthDate()));
        return author;
    }

    public AuthorDTO mapAuthorToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBio(author.getBio());
        authorDTO.setBirthDate(author.getBirthDate().toString());
        return authorDTO;
    }
}
