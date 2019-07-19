package com.intexsoft.web.dto.mapping;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Review;
import com.intexsoft.dao.model.User;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.UserService;
import com.intexsoft.web.dto.request.ReviewRequestDTO;
import com.intexsoft.web.dto.response.ReviewResponseDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
public class ReviewDTOMapper {

    private final BookService bookService;
    private final UserService userService;

    public ReviewDTOMapper(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public Review mapReviewRequestDTOtoReview(ReviewRequestDTO requestDTO){
        Review review = new Review();
        review.setId(requestDTO.getId());
        review.setBook(bookService.getOne(requestDTO.getBookId()));
        review.setComment(requestDTO.getComment());
        review.setRating(requestDTO.getRating());
        review.setUser(userService.getOne(requestDTO.getUserId()));
        return review;
    }

    public ReviewResponseDTO mapReviewToReviewResponseDTO(@Valid Review review){
        User user = review.getUser();
        Book book = review.getBook();
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setBookId(book.getId());
        reviewResponseDTO.setBookName(book.getName());
        reviewResponseDTO.setComment(review.getComment());
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setLocalDateTime(review.getTime());
        reviewResponseDTO.setRating(review.getRating());
        reviewResponseDTO.setUserId(user.getId());
        reviewResponseDTO.setUserName(user.getName());
        reviewResponseDTO.setUserSurname(user.getSurname());
        return reviewResponseDTO;
    }
}
