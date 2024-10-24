package com.example.service.impl;

import com.example.controller.request.SaveUserRequest;
import com.example.controller.response.UserResponse;
import com.example.entity.User;
import com.example.exception.CustomErrorException;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void registerOK() {
        // Given
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setName("alexander");
        saveUserRequest.setEmail("alex@gmail.com");
        saveUserRequest.setUsername("aochoa");
        saveUserRequest.setPassword("12345678");

        User user = new User();
        user.setIdUser(1L);
        user.setName("alexander");
        user.setEmail("alex@gmail.com");
        user.setUsername("aochoa");
        user.setPassword("123456");

        when(userRepository.save(any())).thenReturn(user);
        // When
        UserResponse response = userService.register(saveUserRequest);

        // Then
        assertNotNull(response);
        assertNotNull(response.getIdUser());
        assertEquals(1, response.getIdUser().intValue());
    }

    @Test
    void registerErrorExistEmail() {
        // Given
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setEmail("alex@gmail.com");

        User user = new User();
        user.setIdUser(1L);
        user.setEmail("alex@gmail.com");

        when(userRepository.findByEmail(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        // When
        CustomErrorException errorException = assertThrows(CustomErrorException.class,
                () -> userService.register(saveUserRequest));

        // Then
        assertNotNull(errorException);
    }

}
