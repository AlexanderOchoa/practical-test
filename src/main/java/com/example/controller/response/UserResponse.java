package com.example.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {

    private Long idUser;
    private String name;
    private String username;
    private String email;
}
