package com.example.usercrud.user.service;

import com.example.usercrud.user.dto.UserRequest;
import com.example.usercrud.user.dto.UserResponse;
import com.example.usercrud.user.entity.User;
import com.example.usercrud.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToResponse);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(userRequest.getUsername()) && 
            userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
} 