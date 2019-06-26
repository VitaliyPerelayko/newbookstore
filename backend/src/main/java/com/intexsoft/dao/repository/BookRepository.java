package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository layer for Book entity
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * find book by it's id
     *
     * @param id id of book
     * @return book with the given id
     */
    @Query("FROM Book book JOIN FETCH book.authors WHERE book.id = :id")
    Optional<Book> findBookById(@Param("id") Long id);

    /**
     * find all books from database
     *
     * @return all books ordered by publish date
     */
    @Query("FROM Book book ORDER BY book.publishDate")
    List<Book> findAllOrderedByDate();
}
