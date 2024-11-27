package com.ricardo.scalable.ecommerce.platform.userService.repositories.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @Size(min = 6, max = 25)
    private String password;

    @Transient
    @JsonIgnore
    private boolean admin;

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @Size(min = 6, max = 25) String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 6, max = 25) String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
