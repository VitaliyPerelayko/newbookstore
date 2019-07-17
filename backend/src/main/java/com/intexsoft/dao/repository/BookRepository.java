package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Book;
import com.intexsoft.web.dto.response.BookResponseShortVersionDTO;
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
    @Query("FROM Book book JOIN FETCH book.authors JOIN FETCH book.publisher WHERE book.id = :id")
    Optional<Book> findBookById(@Param("id") Long id);

    /**
     * find all books from database
     *
     * @return all books ordered by publish date
     */
    @Query("FROM Book book JOIN FETCH book.reviews ORDER BY book.publishDate")
    List<Book> findAllOrderedByDate();

    /**
     * @param code code of book
     * @return true if book with the given code exist in database and false otherwise
     */
    boolean existsByCode(String code);

    /**
     * find book by the given code
     *
     * @param code code of book
     * @return book with the given code
     */
    @Query("FROM Book book JOIN FETCH book.authors JOIN FETCH book.publisher WHERE book.code = :code")
    Book findBookByCode(@Param("code") String code);

    /**
     * Insert new number of books to the book with the given id
     *
     * @param id     id of book
     * @param number new number of book
     */
    @Query("UPDATE Book book SET book.number = :number WHERE book.id = :id")
    void insertNumberOfBooks(@Param("id") Long id, @Param("number") Short number);

    /**
     * find all books with it's rating
     *
     * @return List of books
     */
    @Query("FROM Book book JOIN FETCH book.reviews")
    List<Book> findAllWithReviews();

    /**
     * find id of book with the highest average rating
     * @return id
     */
    @Query(nativeQuery = true, value =
            "SELECT book_id FROM (SELECT book_id, AVG(rating) AS avgr FROM reviews GROUP BY book_id ORDER BY avgr DESC LIMIT 1) as bestbook")
    Long findBookIdWhereMaxAvgRating();
}
