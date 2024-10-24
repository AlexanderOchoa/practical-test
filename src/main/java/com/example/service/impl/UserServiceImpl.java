package com.example.service.impl;

import com.example.controller.request.SaveUserRequest;
import com.example.controller.request.UpdateUserRequest;
import com.example.controller.response.SaveUserResponse;
import com.example.entity.User;
import com.example.exception.CustomErrorException;
import com.example.repository.UserRepository;
import com.example.controller.response.UserResponse;
import com.example.security.JWTUtil;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SaveUserResponse register(SaveUserRequest request) {
        User userEmailInDb = userRepository.findByEmail(request.getEmail());

        if (userEmailInDb != null) {
            throw new CustomErrorException("The email of the user exist.");
        }

        User userUsernameInDb = userRepository.findByUsername(request.getUsername());

        if (userUsernameInDb != null) {
            throw new CustomErrorException("The username of the user exist.");
        }

        User userToSave = getUser(request);

        User registeredUser = userRepository.save(userToSave);

        return getSaveUserResponse(registeredUser);
    }

    @Override
    public List<UserResponse> list() {
        List<User> usersInDb = userRepository.findAll();

        return usersInDb.stream()
                .map(this::getGetUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse get(Long idUser) {
        Optional<User> userInDb = userRepository.findById(idUser);

        if (!userInDb.isPresent()) {
            throw new CustomErrorException("The user don't exist.");
        }

        return getGetUserResponse(userInDb.get());
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        Optional<User> userInDb = userRepository.findById(request.getIdUser());

        if (!userInDb.isPresent()) {
            throw new CustomErrorException("The user don't exist.");
        }

        User userEmailInDb = userRepository.findByEmail(request.getEmail());

        if (userEmailInDb != null && !userEmailInDb.getIdUser().equals(request.getIdUser())) {
            throw new CustomErrorException("The email of the user exist.");
        }

        User userUsernameInDb = userRepository.findByUsername(request.getUsername());

        if (userUsernameInDb != null && !userUsernameInDb.getIdUser().equals(request.getIdUser())) {
            throw new CustomErrorException("The username of the user exist.");
        }

        User userToSave = getUpdateUser(request, userInDb.get());

        User updatedUser = userRepository.save(userToSave);

        return getGetUserResponse(updatedUser);
    }

    @Override
    public String delete(Long idUser) {
        Optional<User> userInDb = userRepository.findById(idUser);

        if (!userInDb.isPresent()) {
            throw new CustomErrorException("The user don't exist.");
        }

        userRepository.delete(userInDb.get());

        return "Success process";
    }

    private User getUser(SaveUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        return user;
    }

    private SaveUserResponse getSaveUserResponse(User user) {
        SaveUserResponse response = new SaveUserResponse();
        BeanUtils.copyProperties(user, response);
        response.setToken(JWTUtil.generarToken(user.getUsername()));
        return response;
    }

    private UserResponse getGetUserResponse(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    private User getUpdateUser(UpdateUserRequest request, User user) {
        BeanUtils.copyProperties(request, user);
        return user;
    }

}
