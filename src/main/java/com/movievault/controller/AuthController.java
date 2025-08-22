package com.movievault.controller;

import com.movievault.config.TokenService;
import com.movievault.controller.request.LoginRequest;
import com.movievault.controller.request.UserRequest;
import com.movievault.controller.response.UserResponse;
import com.movievault.entity.User;
import com.movievault.mapper.UserMapper;
import com.movievault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MovieVault/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        User newUser = UserMapper.toUser(request);
        User savedUser = userService.saveUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(),request.password());
        Authentication auth = authManager.authenticate(userAndPass);

        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(tokenService.generateToken(user));
    }
}
