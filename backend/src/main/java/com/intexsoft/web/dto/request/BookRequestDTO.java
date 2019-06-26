package com.intexsoft.web.dto.request;

import com.intexsoft.dao.model.Category;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class BookRequestDTO {

    @PositiveOrZero(message = "Id of book must be positive or zero")
    private Long id;

    @NotBlank(message = "Name of book must be not blank")
    private String name;

    private String description;

    @NotEmpty(message = "List of authors must be not empty")
    private List<
            @NotNull(message = "Each value of list authors must be not null")
            @PositiveOrZero(message = "Each value of list authors must be positive or zero")
                    Long> authorsId;

    @NotBlank(message = "Publish date of book must be not blank")
    private String publishDate;

    @NotNull(message = "Publisher's id of book must be not null")
    @PositiveOrZero(message = "Publisher's id of book must be positive or zero")
    private Long publisherId;

    @NotNull(message = "Price of book must be not null")
    @Positive(message = "Price of book must be positive")
    private BigDecimal price;

    @NotNull(message = "Category of book must be not null")
    private Category category;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<Long> authorsId) {
        this.authorsId = authorsId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
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
}
