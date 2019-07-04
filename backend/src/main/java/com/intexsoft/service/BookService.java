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

    Book findByCode(String code);

    boolean existByCode(String code);

    @Transactional
    Book save(Book book);

    @Transactional
    List<Book> saveAll(List<Book> books);

    @Transactional
    List<Book> saveBatch(List<Book> books);

    @Transactional
    Book update(Book book);

    @Transactional
    void delete(Book book);

    @Transactional
    void deleteById(Long id);
}
