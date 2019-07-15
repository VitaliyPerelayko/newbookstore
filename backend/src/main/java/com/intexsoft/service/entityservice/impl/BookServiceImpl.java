package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.repository.BookRepository;
import com.intexsoft.service.entityservice.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for Road entity.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    /**
     * find all books from database
     *
     * @return List of books
     */
    @Override
    public List<Book> findAllSortByDate() {
        return bookRepository.findAllOrderedByDate();
    }

    /**
     * finds book by it's id
     *
     * @param id id of book
     * @return book with the given id
     */
    @Override
    public Book findById(Long id) {
        return bookRepository.findBookById(id).orElseThrow(() ->
                new RuntimeException("Book with the given id doesn't exist"));
    }

    /**
     * find book by the given code
     *
     * @param code code of book
     * @return book with the given code
     */
    @Override
    public Optional<Book> findByCode(String code) {
        return Optional.ofNullable(bookRepository.findBookByCode(code));
    }

    /**
     * @param code code of book
     * @return true if book with the given code exist in database and false otherwise
     */
    @Override
    public boolean existByCode(String code) {
        return bookRepository.existsByCode(code);
    }

    /**
     * Save new entity Book.
     *
     * @param book book entity
     * @return saved entity
     */
    @Transactional
    @Override
    public Book save(@Valid Book book) {
        validate(book.getId() != null,
                "error.book.haveId");
        validate(!bookRepository.existsByCode(book.getCode()), "error.book.code.notUnique");
        return bookRepository.saveAndFlush(book);
    }

    /**
     * Save all entities from List
     * (use batching)
     *
     * @param books List of books
     * @return List of saved books
     */
    @Transactional
    @Override
    public List<Book> saveAll(List<@Valid Book> books) {
        books.forEach(book -> {
            validate(book.getId() != null, "error.book.haveId");
            validate(bookRepository.existsByCode(book.getCode()), "error.book.code.notUnique");
        });
        return bookRepository.saveAll(books);
    }

    /**
     * Save all entities from List
     * (use batching)
     * (this method is used for save imported data)
     *
     * @param books List of books
     * @return List of saved books
     */
    @Transactional
    @Override
    public List<Book> saveBatch(List<@Valid Book> books) {
        books.forEach(book -> {
            Optional<Book> updatedBook = findByCode(book.getCode());
            updatedBook.ifPresent(value -> book.setCode(value.getCode()));
        });
        return bookRepository.saveAll(books);
    }

    /**
     * Update entity Book.
     *
     * @param book book entity
     * @return updated entity
     */
    @Transactional
    @Override
    public Book update(@Valid Book book) {
        Long id = book.getId();
        validate(id == null,
                "error.book.haveNoId");
        isExist(id);
        Optional<Book> duplicate = findByCode(book.getCode());
        duplicate.ifPresent(value -> validate(id.equals(value.getId()), "error.book.code.notUnique"));
        return bookRepository.saveAndFlush(book);
    }

    /**
     * Insert new number of books to the book with the given id
     *
     * @param id     id of book
     * @param number new number of book
     */
    @Transactional
    @Override
    public void setNumberOfBooks(Long id, Short number) {
        validate(number < 0, "Error. Number of books must be positive");
        isExist(id);
        bookRepository.insertNumberOfBooks(id, number);
    }

    /**
     * Deletes a given entity.
     *
     * @param book book entity
     */
    @Override
    @Transactional
    public void delete(@Valid Book book) {
        Long id = book.getId();
        validate(id == null, "error.book.haveId");
        isExist(id);
        bookRepository.delete(book);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        isExist(id);
        bookRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }

    private void isExist(Long id) {
        validate(!bookRepository.existsById(id), "error.book.id.notExist");
    }
}
