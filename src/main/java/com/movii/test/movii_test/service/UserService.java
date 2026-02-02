package com.movii.test.movii_test.service;

import com.movii.test.movii_test.client.CleverTapClient;
import com.movii.test.movii_test.dto.UserDTO;
import com.movii.test.movii_test.mapper.UserMapper;
import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.repository.UserRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CleverTapClient cleverTapClient;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, CleverTapClient cleverTapClient, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.cleverTapClient = cleverTapClient;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userRepository.existsByIdentity(user.getIdentity())) {
            throw new IllegalArgumentException("User with identity " + user.getIdentity() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        uploadToCleverTap(savedUser);

        return userMapper.toDto(savedUser);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByIdentity(user.getIdentity())) {
            throw new IllegalArgumentException("User with identity " + user.getIdentity() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        uploadToCleverTap(savedUser);

        return savedUser;
    }

    @Async
    protected void uploadToCleverTap(User user) {
        cleverTapClient.uploadProfile(user);
    }
}