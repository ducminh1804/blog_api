package com.ducminh.blogapi.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationRequest {
    @NotBlank
    @Size(min = 3)
    private String firstName;


    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Email(message = "Must be email")
    private String email;
}
