package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * @param name name of author
     * @return true if author exist in database and false otherwise
     */
    boolean existsByName(String name);

    /**
     * find author by the given name
     *
     * @param name name of author
     * @return author with the given name
     */
    Author findAuthorByName(String name);

    /**
     * find authors of the given book
     *
     * @param book book
     * @return list of authors
     */
    List<Author> findAllByBooks(Book book);
}
