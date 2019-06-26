package com.intexsoft.service;

import com.intexsoft.dao.model.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Book entity.
 */
public interface BookService {
    Book findById(Long id);

    List<Book> findAllSortByDate();

    @Transactional
    Book save(Book book);

    @Transactional
    Book update(Book book);

    @Transactional
    void delete(Book book);

    @Transactional
    void deleteById(Long id);
}
