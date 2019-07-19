package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.repository.BookRepository;
import com.intexsoft.service.entityservice.BookService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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
     * @return List of books ordered by publishDate
     */
    @Override
    public List<Book> findAllSortByDate() {
        return bookRepository.findAllOrderedByDate();
    }

    /**
     * find all books from database
     *
     * @return List of books
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAllWithReviews();
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
     * finds book with lazy (authors, publishers, reviews) by it's id
     *
     * @param id id of book
     * @return book with the given id
     */
    @Override
    public Book findByIdLazy(Long id){
        return bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book with the given id doesn't exist"));
    }

    /**
     * I use this method in controllers: I set reference to the entity instead of real entity, when map RequestDTO
     * object to real object.
     *
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link javax.persistence.EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
     */
    @Override
    public Book getOne(Long id){
        return bookRepository.getOne(id);
    }

    /**
     * find book with the highest rating
     *
     * @return book
     */
    @Override
    public Book findByTheHighestRating() {
        Long id = bookRepository.findBookIdWhereMaxAvgRating();
        if (id == null) {
            throw new IllegalStateException("There aren't any books with review");
        }
        return findById(id);
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
     * (this method is used for saveAndUpdate imported data)
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
     * Insert new number of books to the book in database with the given id
     *
     * @param id     id of book
     * @param number new number of book
     */
    @Transactional
    @Override
    public void setNumberOfBook(Long id, Short number) {
        validate(number < 0, "Error. Number of books must be positive");
        isExist(id);
        bookRepository.insertNumberOfBooks(id, number);
    }

    /**
     * add some short value to number in the book with the given id
     *
     * @param id     id of book
     * @param number value that will be added
     */
    @Transactional
    @Override
    public void setNumberOfBookPlus(Long id, Short number) {
        validate(number < 0, "Error. Number of books must be positive");
        isExist(id);
        bookRepository.insertNumberOfBooksPlus(id, number);
    }

    /**
     * subtract some short value from number in the book with the given id
     *
     * @param id     id of book
     * @param number value that will be subtracted
     */
    @Transactional
    @Override
    public void setNumberOfBookSubtract(Long id, Short number) {
        validate(number < 0, "Error. Number of books must be positive");
        Assert.state(findByIdLazy(id).getNumber() > number,
                "Error. Number of books in database less than number which you try to subtract");
        bookRepository.insertNumberOfBooksSubtract(id, number);
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
