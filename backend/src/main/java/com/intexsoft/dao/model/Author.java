package com.intexsoft.dao.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Name of author must be not blank")
    @Size(max = 50, message = "Number of characters in author's name must be less than 50")
    private String name;

    @Size(max = 200, message = "Number of characters in author's biography must be less 200")
    private String bio;

    @NotNull(message = "BirthDate of author must be not null")
    @Past(message = "Author can not be born in the future")
    private LocalDate birthDate;

    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "authors")
    private Set<Book> books;

    public Author(String name, String bio, LocalDate birthDate) {
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    public Author() {
    }

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
