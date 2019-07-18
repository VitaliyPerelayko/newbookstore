package com.intexsoft.dao.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Code of book must be not blank")
    @Size(max = 20, message = "Number of characters in book's code must be less than 20")
    private String code;

    @Column(nullable = false)
    @NotBlank(message = "Name of book must be not blank")
    @Size(max = 50, message = "Number of characters in book's title must be less than 50")
    private String name;

    @Size(max = 200, message = "Number of characters in book's description must be less than 200")
    private String description;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "book_has_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @NotEmpty(message = "List of authors must be not empty")
    private Set<
            @NotNull(message = "Each author name must be not null")
            Author> authors;

    @Column(nullable = false)
    @NotNull(message = "Publish date of book must be not null")
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Publisher of book must be mot null")
    @Convert
    private Publisher publisher;

    @Column(nullable = false)
    @NotNull(message = "Price of book must be not null")
    @Positive(message = "Price of book must be positive")
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category of book must be not null")
    private Category category;

    @PositiveOrZero(message = "Number of books must be positive")
    private Short number;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "book")
    private Set<OrderProducts> orderProducts;

    public Book(String code, String name, String description, Set<Author> authors, LocalDate publishDate,
                Publisher publisher, BigDecimal price, Category category, Short number) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.price = price;
        this.category = category;
        this.number = number;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
}
