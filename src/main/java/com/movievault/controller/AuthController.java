package com.movievault.controller;

import com.movievault.controller.request.UserRequest;
import com.movievault.controller.response.UserResponse;
import com.movievault.entity.User;
import com.movievault.mapper.UserMapper;
import com.movievault.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MovieVault/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        User newUser = UserMapper.toUser(request);
        User savedUser = authService.saveUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserResponse(savedUser));
    }

}
