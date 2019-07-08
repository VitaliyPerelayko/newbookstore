package com.intexsoft.service.importdata.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.intexsoft.service.importdata.impl.adapters.csv.LocalDateConverter;
import com.intexsoft.service.importdata.impl.adapters.xml.LocalDateAdapter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

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
public class AuthorPOJO implements ObjectsForBindings {

    @NotBlank(message = "authorPOJO.name.notBlank")
    @Size(max = 50, message = "authorPOJO.name.size")
    @CsvBindByName(column = "name")
    private String name;

    @Size(max = 200, message = "authorPOJO.bio.size")
    @CsvBindByName(column = "bio")
    private String bio;

    @NotNull(message = "authorPOJO.birthDate.notNull")
    @Past(message = "authorPOJO.birthDate.past")
    @CsvBindByName(column = "birthDate")
    @CsvCustomBindByName(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
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

    @Override
    public String getUniqueValue() {
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
        return Objects.hash(name, bio, birthDate);
    }
}
