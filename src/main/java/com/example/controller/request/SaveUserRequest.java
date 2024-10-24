package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SaveUserRequest {

    @NotNull(message = "name required")
    @Length(min = 2, max = 100)
    private String name;

    @NotNull(message = "email required")
    @Email
    private String email;

    @NotNull(message = "username required")
    @Length(min = 5, max = 100)
    private String username;

    @NotNull(message = "password required")
    @Length(min = 8, max = 100)
    private String password;
}
