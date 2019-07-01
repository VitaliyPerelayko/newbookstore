package com.intexsoft.dao.importdata.pojo;

import com.intexsoft.dao.importdata.impl.adapters.CategotyAdapter;
import com.intexsoft.dao.importdata.impl.adapters.LocalDateAdapter;
import com.intexsoft.dao.model.Category;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "book")
public class BookPOJO {

    private String name;

    private String code;

    private String description;

    private List<String> authors;

    private LocalDate publishDate;

    private String publisher;

    private BigDecimal price;

    private Category category;

    public String getName() {
        return name;
    }

    @XmlElement(name = "title")
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    @XmlElement
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "name")
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    @XmlElement
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    @XmlElement
    @XmlJavaTypeAdapter(CategotyAdapter.class)
    public void setCategory(Category category) {
        this.category = category;
    }
}