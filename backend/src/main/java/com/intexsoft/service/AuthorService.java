package com.intexsoft.service;


import com.intexsoft.dao.model.Author;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Author entity.
 */
public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author findByName(String name);

    boolean existByName(String name);

    @Transactional
    Author save(Author author);

    @Transactional
    List<Author> saveAll(List<Author> authors);

    @Transactional
    List<Author> saveBatch(List<Author> authors);

    @Transactional
    Author update(Author author);

    @Transactional
    void delete(Author author);

    @Transactional
    void deleteById(Long id);
}
