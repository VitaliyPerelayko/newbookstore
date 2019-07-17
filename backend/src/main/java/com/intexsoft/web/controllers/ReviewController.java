package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Review;
import com.intexsoft.service.entityservice.ReviewService;
import com.intexsoft.web.dto.mapping.ReviewDTOMapper;
import com.intexsoft.web.dto.request.ReviewRequestDTO;
import com.intexsoft.web.dto.response.ReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewDTOMapper reviewDTOMapper;
    private final ReviewService reviewService;

    public ReviewController(ReviewDTOMapper reviewDTOMapper, ReviewService reviewService) {
        this.reviewDTOMapper = reviewDTOMapper;
        this.reviewService = reviewService;
    }

    @GetMapping("/book_and_user")
    public ResponseEntity<List<ReviewResponseDTO>> getByBookAndUser(@RequestParam Long bookId, @RequestParam Long userId) {
        List<Review> reviews = reviewService.findByBookAndUser(bookId, userId);
        return ResponseEntity.ok(reviews.stream()
                .map(reviewDTOMapper::mapReviewToReviewResponseDTO).collect(Collectors.toList()));
    }

    @GetMapping("/book")
    public ResponseEntity<List<ReviewResponseDTO>> getAllByUser(@RequestParam Long id) {
        List<Review> reviews = reviewService.findAllByBook(id);
        return ResponseEntity.ok(reviews.stream()
                .map(reviewDTOMapper::mapReviewToReviewResponseDTO).collect(Collectors.toList()));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponseDTO>> getAllByBook(@RequestParam Long id) {
        List<Review> reviews = reviewService.findAllByUser(id);
        return ResponseEntity.ok(reviews.stream()
                .map(reviewDTOMapper::mapReviewToReviewResponseDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getById(@PathVariable Long id) {
        Review review = reviewService.findById(id).orElseThrow(() ->
                new RuntimeException("Review with the given id wasn't found in database"));
        return ResponseEntity.ok(reviewDTOMapper.mapReviewToReviewResponseDTO(review));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> save(@Valid @RequestBody ReviewRequestDTO requestDTO) {
        Review review = reviewDTOMapper.mapReviewRequestDTOtoReview(requestDTO);
        review.setTime(LocalDateTime.now());
        return ResponseEntity.ok(reviewDTOMapper.mapReviewToReviewResponseDTO(
                reviewService.save(review)
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')  or #id == authentication.principal.id")
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody ReviewRequestDTO requestDTO) {
        if (!id.equals(requestDTO.getId())) {
            throw new IllegalStateException("Id in URL path and id in request body must be the same");
        }
        Review review = reviewDTOMapper.mapReviewRequestDTOtoReview(requestDTO);
        return ResponseEntity.ok(reviewDTOMapper.mapReviewToReviewResponseDTO(
                reviewService.update(review)
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')  or #id == authentication.principal.id")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }

}
