package com.example.service;

import com.example.controller.request.SaveUserRequest;
import com.example.controller.request.UpdateUserRequest;
import com.example.controller.response.SaveUserResponse;
import com.example.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    SaveUserResponse register(SaveUserRequest request);
    List<UserResponse> list();
    UserResponse get(Long idUser);
    UserResponse update(UpdateUserRequest request);
    String delete(Long idUser);
}
