package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLogInService {

    private final UserRepository userRepository;

    public UserLogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findValidUser(String email, String password) {
        return userRepository.findByEmail(email).stream()
                .filter(user -> user.getPassword().equals(password)
                && user.is_email_verified())
                .findFirst();
    }
}
