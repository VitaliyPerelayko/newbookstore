package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Review;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> findByBookAndUser(Long bookId, Long userId);

    List<Review> findAllByBook(Long bookId);

    List<Review> findAllByUser(Long userId);

    Optional<Review> findById(Long id);

    @Transactional
    Review save(Review review);

    @Transactional
    Review update(Review review);

    @Transactional
    void delete(Long id);
}
