package com.intexsoft.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class AuthorDTO {

    @PositiveOrZero(message = "Id of author must be positive or zero")
    private Long id;

    @NotBlank(message = "Name of author must be not blank")
    @Size(max = 50, message = "Amount of characters in author's name must be less than 50")
    private String name;

    @Size(max = 200, message = "Amount of characters in author's biography must be less 200")
    private String bio;

    @NotBlank(message = "BirthDate of author must be not blank")
    private String birthDate;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
