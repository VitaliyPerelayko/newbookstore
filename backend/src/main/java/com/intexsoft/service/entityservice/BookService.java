package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for Book entity.
 */
public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    List<Book> findAllSortByDate();

    Book findByIdLazy(Long id);

    Book getOne(Long id);

    Book findByTheHighestRating();

    Optional<Book> findByCode(String code);

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
    void setNumberOfBook(Long id, Short number);

    @Transactional
    void delete(Book book);

    @Transactional
    void deleteById(Long id);
}
