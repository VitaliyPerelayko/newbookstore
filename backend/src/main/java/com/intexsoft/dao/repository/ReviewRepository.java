package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * find all user's reviews of the given book
     *
     * @param book book
     * @param user user
     * @return List of Review
     */
    List<Review> findAllByBookAndUserOrderByTime(Book book, User user);

    /**
     * find all user's book reviews
     *
     * @param user user
     * @return List of Review
     */
    List<Review> findAllByUserOrderByTime(User user);


    /**
     * find all reviews of the given book
     *
     * @param book book
     * @return List of Review
     */
    List<Review> findAllByBookOrderByTime(Book book);



}
