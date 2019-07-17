package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * find all user's reviews of the given book
     *
     * @param book book
     * @param user user
     * @return List of Review
     */
    @Query("FROM Review rev JOIN FETCH rev.book JOIN FETCH rev.user WHERE rev.book = :book AND rev.user = :user ORDER BY rev.time")
    List<Review> findAllByBookAndUserOrderByTime(@Param("book") Book book, @Param("user") User user);

    /**
     * find all user's book reviews
     *
     * @param user user
     * @return List of Review
     */
    @Query("FROM Review rev JOIN FETCH rev.book JOIN FETCH rev.user WHERE rev.user = :user ORDER BY rev.time")
    List<Review> findAllByUserOrderByTime(@Param("user") User user);


    /**
     * find all reviews of the given book
     *
     * @param book book
     * @return List of Review
     */
    @Query("FROM Review rev JOIN FETCH rev.book JOIN FETCH rev.user WHERE rev.book = :book ORDER BY rev.time")
    List<Review> findAllByBookOrderByTime(@Param("book") Book book);

    /**
     * find entity by the given id
     *
     * @param id id of review
     * @return review
     */
    @Query("FROM Review review JOIN FETCH review.book JOIN FETCH review.user WHERE review.id = :id")
    Review findOneById(@Param("id") Long id);
}
