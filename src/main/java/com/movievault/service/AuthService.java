package com.movievault.service;

import com.movievault.entity.User;
import com.movievault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
