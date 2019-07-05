package com.intexsoft.web.dto.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class BookRequestDTO {

    @PositiveOrZero(message = "Id of book must be positive or zero")
    private Long id;

    @NotBlank(message = "Name of book must be not blank")
    @Size(max = 50, message = "Amount of characters in book's title must be less than 50")
    private String name;

    @NotBlank(message = "Code of book must be not blank")
    @Size(max = 20, message = "Amount of characters in book's code must be less than 20")
    private String code;

    @Size(max = 200, message = "Amount of characters in book's description must be less than 200")
    private String description;

    @NotEmpty(message = "List of authors must be not empty")
    private List<
            @NotBlank(message = "Each author name must be not blank")
                    String> authors;

    @NotBlank(message = "Publish date of book must be not blank")
    private String publishDate;

    @NotBlank(message = "Publisher of book must be mot blank")
    private String publisher;

    @NotNull(message = "Price of book must be not null")
    @Positive(message = "Price of book must be positive")
    private BigDecimal price;

    @NotBlank(message = "Category of book must be not blank")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
