package com.intexsoft.web.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewRequestDTO {

    private Long id;

    @NotNull(message = "book's rating must be not null")
    @Min(value = 0, message = "book's rating (in entity review) must be higher then 0")
    @Max(value = 10, message = "book's rating (in entity review) must be lower then 10")
    private Short rating;

    @Size(message = "Number of characters in review's comment must be less than 300")
    private String comment;

    private Long userId;

    @NotNull(message = "Field bookId in the ReviewRequestDTO must be not null. Review must be associated with some book.")
    private Long bookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
