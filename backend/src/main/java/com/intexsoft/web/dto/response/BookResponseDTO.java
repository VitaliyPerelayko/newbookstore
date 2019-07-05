package com.intexsoft.web.dto.response;

import com.intexsoft.web.dto.PublisherDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for response entity Book
 */
public class BookResponseDTO {

    private Long id;

    private String name;

    private String code;

    private String description;

    private List<AuthorResponseInBookDTO> authorsName;

    private String publishDate;

    private PublisherDTO publisher;

    private BigDecimal price;

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

    public List<AuthorResponseInBookDTO> getAuthors() {
        return authorsName;
    }

    public void setAuthors(List<AuthorResponseInBookDTO> authors) {
        this.authorsName = authors;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
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
