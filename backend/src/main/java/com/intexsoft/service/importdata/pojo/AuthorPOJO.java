package com.intexsoft.service.importdata.pojo;

import com.intexsoft.service.importdata.impl.adapters.LocalDateAdapter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "author")
public class AuthorPOJO {

    @NotBlank(message = "authorPOJO.name.notBlank")
    @Size(max = 50, message = "authorPOJO.name.size")
    private String name;

    @Size(max = 200, message = "authorPOJO.bio.size")
    private String bio;

    @NotNull(message = "authorPOJO.birthDate.notNull")
    @Past(message = "authorPOJO.birthDate.past")
    private LocalDate birthDate;

    public AuthorPOJO(@NotBlank(message = "authorPOJO.name.notBlank")
                      @Size(max = 50, message = "authorPOJO.name.size") String name,
                      @Size(max = 200, message = "authorPOJO.bio.size") String bio,
                      @NotNull(message = "authorPOJO.birthDate.notNull")
                      @Past(message = "authorPOJO.birthDate.past") LocalDate birthDate) {
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    public AuthorPOJO() {
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    @XmlElement
    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorPOJO that = (AuthorPOJO) o;
        return name.equals(that.name) &&
                Objects.equals(bio, that.bio) &&
                birthDate.equals(that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
