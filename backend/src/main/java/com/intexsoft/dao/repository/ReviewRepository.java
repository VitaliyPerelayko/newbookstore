package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * find all user's reviews of the given book
     *
     * @param bookId book
     * @param userId user
     * @return List of Review
     */
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT * FROM reviews rev INNER JOIN book b ON rev.book_id = b.id INNER JOIN users u on rev.user_id = u.id WHERE rev.book_id = :bookId AND rev.user_id = :userId")
    List<Review> findAllByBookAndUserOrderByTime(@Param("bookId") Long bookId, @Param("userId") Long userId);

    /**
     * find all user's book reviews
     *
     * @param userId user
     * @return List of Review
     */
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT * FROM reviews rev INNER JOIN book b ON rev.book_id = b.id INNER JOIN users u on rev.user_id = u.id WHERE rev.user_id = :userId")
    List<Review> findAllByUserOrderByTime(@Param("userId") Long userId);


    /**
     * find all reviews of the given book
     *
     * @param bookId book
     * @return List of Review
     */
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT * FROM reviews rev INNER JOIN book b ON rev.book_id = b.id INNER JOIN users u on rev.user_id = u.id WHERE rev.book_id = :bookId")
    List<Review> findAllByBookOrderByTime(@Param("bookId") Long bookId);

    /**
     * find entity by the given id
     *
     * @param id id of review
     * @return review
     */
    @Query("FROM Review review JOIN FETCH ALL PROPERTIES WHERE review.id = :id")
    Review findOneById(@Param("id") Long id);
}
