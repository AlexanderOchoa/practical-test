package com.example.controller;

import com.example.controller.request.SaveUserRequest;
import com.example.controller.request.UpdateUserRequest;
import com.example.controller.response.SaveUserResponse;
import com.example.controller.response.UserResponse;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<SaveUserResponse> register(@Valid @RequestBody SaveUserRequest request) {
        SaveUserResponse response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> response = userService.list();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{idUser}", produces = "application/json")
    public @ResponseBody ResponseEntity<UserResponse> get(@PathVariable(value = "idUser") Long idUser) {
        UserResponse response = userService.get(idUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<UserResponse> update(@Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idUser}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> delete(@PathVariable(value = "idUser") Long idUser) {
        String response = userService.delete(idUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
