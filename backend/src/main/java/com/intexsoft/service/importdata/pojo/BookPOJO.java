package com.intexsoft.service.importdata.pojo;

import com.intexsoft.dao.model.Category;
import com.intexsoft.service.importdata.impl.adapters.csv.BigDecimalConverter;
import com.intexsoft.service.importdata.impl.adapters.csv.CategoryConverter;
import com.intexsoft.service.importdata.impl.adapters.csv.LocalDateConverter;
import com.intexsoft.service.importdata.impl.adapters.xml.CategoryAdapter;
import com.intexsoft.service.importdata.impl.adapters.xml.LocalDateAdapter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@XmlRootElement(name = "book")
public class BookPOJO implements ObjectsForBindings {

    @NotBlank(message = "bookPOJO.name.notBlank")
    @Size(max = 50, message = "bookPOJO.name.size")
    @CsvBindByName(column = "title")
    private String name;

    @NotBlank(message = "bookPOJO.code.notBlank")
    @Size(max = 20, message = "bookPOJO.code.size")
    @CsvBindByName(column = "code")
    private String code;

    @Size(max = 200, message = "bookPOJO.description.size")
    @CsvBindByName(column = "description")
    private String description;

    @NotEmpty(message = "bookPOJO.authors.notEmpty")
    @CsvBindAndSplitByName(column = "authors", elementType = String.class, splitOn = "\\|")
    private Set<
            @NotBlank(message = "bookPOJO.authors.name.notBlank")
                    String> authors;

    @NotNull(message = "bookPOJO.publishDate.notNull")
    @CsvBindByName(column = "publishDate")
    @CsvCustomBindByName(converter = LocalDateConverter.class)
    private LocalDate publishDate;

    @NotBlank(message = "bookPOJO.publisher.notBlank")
    @CsvBindByName(column = "publisher")
    private String publisher;

    @NotNull(message = "bookPOJO.price.notNull")
    @Positive(message = "bookPOJO.price.positive")
    @CsvBindByName(column = "price")
    @CsvCustomBindByName(converter = BigDecimalConverter.class)
    private BigDecimal price;

    @NotNull(message = "bookPOJO.category.notNull")
    @CsvBindByName(column = "category")
    @CsvCustomBindByName(converter = CategoryConverter.class)
    private Category category;

    public BookPOJO(@NotBlank(message = "bookPOJO.name.notBlank")
                    @Size(max = 50, message = "bookPOJO.name.size") String name,
                    @NotBlank(message = "bookPOJO.code.notBlank")
                    @Size(max = 20, message = "bookPOJO.code.size") String code,
                    @Size(max = 200, message = "bookPOJO.description.size") String description,
                    @NotEmpty(message = "bookPOJO.authors.notEmpty")
                            Set<@NotBlank(message = "bookPOJO.authors.name.notBlank") String> authors,
                    @NotNull(message = "bookPOJO.publishDate.notNull") LocalDate publishDate,
                    @NotBlank(message = "bookPOJO.publisher.notBlank") String publisher,
                    @NotNull(message = "bookPOJO.price.notNull")
                    @Positive(message = "bookPOJO.price.positive") BigDecimal price, Category category) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.authors = authors;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.price = price;
        this.category = category;
    }

    public BookPOJO() {
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "title")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUniqueValue() {
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

    public Set<String> getAuthors() {
        return authors;
    }

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "name")
    public void setAuthors(Set<String> authors) {
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
    @XmlJavaTypeAdapter(CategoryAdapter.class)
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPOJO bookPOJO = (BookPOJO) o;
        return name.equals(bookPOJO.name) &&
                code.equals(bookPOJO.code) &&
                Objects.equals(description, bookPOJO.description) &&
                authors.equals(bookPOJO.authors) &&
                publishDate.equals(bookPOJO.publishDate) &&
                publisher.equals(bookPOJO.publisher) &&
                price.equals(bookPOJO.price) &&
                category == bookPOJO.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, description, authors, publishDate, publisher,
                price, category);
    }
}
