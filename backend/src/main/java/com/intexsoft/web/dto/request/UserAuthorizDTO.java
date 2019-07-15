package com.intexsoft.web.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserAuthorizDTO {

    @NotBlank(message = "Username must be not blank")
    @Size(max = 50, message = "Number of characters for user's username must be less then 50")
    private String username;

    @NotBlank(message = "Password must be not blank")
    private String password;

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
