package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    List<Review> findByBookAndUser(Book book, User user);

    List<Review> findAllByBook(Book book);

    List<Review> findAllByUser(User user);

    @Transactional
    Review save(Review review);

    @Transactional
    Review update(Review review);

    @Transactional
    void delete(Long id);
}
