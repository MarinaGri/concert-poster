package com.technokratos.service.impl;

import com.technokratos.dto.request.UserRequest;
import com.technokratos.exception.UserAlreadyExistsException;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import com.technokratos.service.UserService;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID addUser(UserRequest newUser) {
        String email = newUser.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists with email: " + email);
        }

        UserEntity user = userMapper.toEntity(newUser);
        user.setHashPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(user).getId();
    }
}
