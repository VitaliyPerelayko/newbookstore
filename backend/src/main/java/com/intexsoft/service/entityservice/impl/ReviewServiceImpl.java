package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.repository.ReviewRepository;
import com.intexsoft.service.entityservice.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * find all user's reviews of the given book
     *
     * @param bookId id of book
     * @param userId id of user
     * @return List of Review
     */
    @Override
    public List<Review> findByBookAndUser(Long bookId, Long userId) {
        return reviewRepository.findAllByBookAndUserOrderByTime(bookId, userId);
    }

    /**
     * find all reviews of the given book
     *
     * @param bookId id of book
     * @return List of Review
     */
    @Override
    public List<Review> findAllByBook(Long bookId) {
        return reviewRepository.findAllByBookOrderByTime(bookId);
    }

    /**
     * find all user's book reviews
     *
     * @param userId id of user
     * @return List of Review
     */
    @Override
    public List<Review> findAllByUser(Long userId) {
        return reviewRepository.findAllByUserOrderByTime(userId);
    }

    /**
     * find review by the given id
     *
     * @param id id of review
     * @return Review
     */
    @Override
    public Optional<Review> findById(Long id){
        return Optional.ofNullable(reviewRepository.findOneById(id));
    }

    /**
     * save the given review
     *
     * @param review review
     * @return saved review
     */
    @Override
    @Transactional
    public Review save(@Valid Review review) {
        validate(review.getId() != null, "book which will be saved must hasn't id");
        return reviewRepository.saveAndFlush(review);
    }

    /**
     * update the given review
     *
     * @param review review
     * @return updated review
     */
    @Override
    @Transactional
    public Review update(@Valid Review review) {
        validate(review.getId() == null, "book which will be update must has id");
        return reviewRepository.saveAndFlush(review);
    }

    /**
     * delete the given review
     *
     * @param id id of review
     */
    @Override
    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    private void validate(boolean expression, String message) {
        if (expression) {
            throw new IllegalStateException(message);
        }
    }


}
