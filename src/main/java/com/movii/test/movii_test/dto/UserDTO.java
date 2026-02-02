package com.movii.test.movii_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message = "Identity is required")
    private String identity;
    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    private String email;
    @NotBlank(message = "Name is required")
    private String name;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
