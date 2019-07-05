package com.intexsoft.service.importdata.pojo.mapping;

import com.intexsoft.dao.model.Author;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import org.springframework.stereotype.Service;

@Service
public class AuthorImportMapper {

    public Author mapAuthorPOJOToAuthor(AuthorPOJO authorPOJO) {
        Author author = new Author();
        author.setName(authorPOJO.getUniqueValue());
        author.setBio(authorPOJO.getBio());
        author.setBirthDate(authorPOJO.getBirthDate());
        return author;
    }
}
