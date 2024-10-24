package com.example.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveUserResponse extends UserResponse {

    private String token;
}
