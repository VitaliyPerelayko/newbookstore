package com.intexsoft.web.dto.request;

import com.intexsoft.validation.annotations.ValidEmail;

import javax.validation.constraints.*;

public class UserRequestDTO {

    @PositiveOrZero(message = "User's id must be positive number")
    @Digits(integer = 6, fraction = 0, message = "User's id must be integer number")
    private Long id;

    @Size(max = 50, message = "Amount of characters in user's name must be less than 50")
    private String name;

    @Size(max = 50, message = "Amount of characters in user's surname must be less than 50")
    private String surname;

    @Size(max = 15, message = "Amount of characters in user's phone must be less than 15")
    private String phone;

    @Size(max = 50, message = "Amount of characters in user's e_mail must be less than 50")
    @ValidEmail(message = "User's e_mail invalid. The string has to be a well-formed email address")
    private String e_mail;

    @NotBlank(message = "user's username must be not blank")
    @Size(max = 50, message = "Amount of characters in user's username must be less than 50")
    private String username;

    @NotBlank(message = "user's password must be not blank")
    @Size(max = 100, message = "Amount of characters in user's e_mail must be less than 100")
    private String password;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
