package com.intexsoft.dao.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "book's rating must be not null")
    @Min(value = 0, message = "book's rating (in entity review) must be higher then 0")
    @Max(value = 10, message = "book's rating (in entity review) must be lower then 10")
    private Byte rating;

    @Column(length = 300)
    @Size(message = "Number of characters in review's comment must be less than 300")
    private String comment;

    @Column(nullable = false)
    @NotNull(message = "review's time must be not null")
    @Past(message = "Review's time must be in the past. User can't make review in the future")
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Field book in the review entity must be not null. Review must be associated with some book.")
    private Book book;

    public Review(Byte rating, String comment, User user, Book book) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.book = book;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
