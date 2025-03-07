package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByIdAndEmail(Long id, String email) {
        return userRepository.findById(id).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
