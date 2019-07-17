package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.model.User;
import com.intexsoft.dao.repository.ReviewRepository;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.ReviewService;
import com.intexsoft.service.entityservice.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BookService bookService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.userService = userService;
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
        Book book = bookService.findById(bookId);
        User user = userService.findById(userId).orElseThrow(() ->
                new IllegalStateException("User with the given id wasn't found in database"));
        return reviewRepository.findAllByBookAndUserOrderByTime(book, user);
    }

    /**
     * find all reviews of the given book
     *
     * @param bookId id of book
     * @return List of Review
     */
    @Override
    public List<Review> findAllByBook(Long bookId) {
        Book book = bookService.findById(bookId);
        return reviewRepository.findAllByBookOrderByTime(book);
    }

    /**
     * find all user's book reviews
     *
     * @param userId id of user
     * @return List of Review
     */
    @Override
    public List<Review> findAllByUser(Long userId) {
        User user = userService.findById(userId).orElseThrow(() ->
                new IllegalStateException("User with the given id wasn't found in database"));
        return reviewRepository.findAllByUserOrderByTime(user);
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
