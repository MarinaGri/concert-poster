package com.technokratos.service.impl;

import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.exception.UserAlreadyExistsException;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import com.technokratos.service.UserService;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UUID addUser(UserExtendedRequest newUser) {
        String email = newUser.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists with email: " + email);
        }

        UserEntity user = userMapper.toEntity(newUser);
        user.setHashPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public UserResponse login(UserRequest loginRequest) {
        String email = loginRequest.getEmail();
        UserEntity user = getUserByEmail(email);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getHashPassword())) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional
    public UserEntity getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

}
