package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Review;
import com.intexsoft.service.entityservice.ReviewService;
import com.intexsoft.web.dto.mapping.ReviewDTOMapper;
import com.intexsoft.web.dto.request.ReviewRequestDTO;
import com.intexsoft.web.dto.response.ReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewDTOMapper reviewDTOMapper;
    private final ReviewService reviewService;

    public ReviewController(ReviewDTOMapper reviewDTOMapper, ReviewService reviewService) {
        this.reviewDTOMapper = reviewDTOMapper;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> save(@Valid @RequestBody ReviewRequestDTO requestDTO){
        Review review = reviewDTOMapper.mapReviewRequestDTOtoReview(requestDTO);
        review.setTime(LocalDateTime.now());
        return ResponseEntity.ok(reviewDTOMapper.mapReviewToReviewResponseDTO(
                reviewService.save(review)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> save(@PathVariable Long id,
                                                  @Valid @RequestBody ReviewRequestDTO requestDTO){
        if (!id.equals(requestDTO.getId())){
            throw new IllegalStateException("Id in URL path and id in request body must be the same");
        }
        Review review = reviewDTOMapper.mapReviewRequestDTOtoReview(requestDTO);
        return ResponseEntity.ok(reviewDTOMapper.mapReviewToReviewResponseDTO(
                reviewService.update(review)
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        reviewService.delete(id);
    }

}
