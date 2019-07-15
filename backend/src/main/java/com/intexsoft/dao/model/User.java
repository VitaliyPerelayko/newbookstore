package com.intexsoft.dao.model;

import com.intexsoft.validation.annotations.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Number of characters in user's name must be less than 50")
    private String name;

    @Size(max = 50, message = "Number of characters in user's surname must be less than 50")
    private String surname;

    @Size(max = 15, message = "Number of characters in user's phone must be less than 15")
    private String phone;

    @Size(max = 50, message = "Number of characters in user's e_mail must be less than 50")
    @ValidEmail(message = "User's e_mail invalid. The string has to be a well-formed email address")
    private String e_mail;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "user's username must be not blank")
    @Size(max = 50, message = "Number of characters in user's username must be less than 50")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "user's password must be not blank")
    @Size(max = 100, message = "Number of characters in user's e_mail must be less than 100")
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_has_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotEmpty(message = "user's roles must be not empty")
    private Set<
            @NotNull(message = "each user's role must be not null")
            Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    public User(String name, String surname, String phone, String e_mail, String username, String password,
    Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.e_mail = e_mail;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
}
