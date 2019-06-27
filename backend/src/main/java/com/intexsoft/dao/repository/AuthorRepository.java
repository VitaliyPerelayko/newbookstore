package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
