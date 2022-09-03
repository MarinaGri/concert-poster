package com.technokratos.service;

import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;

import java.util.UUID;

public interface UserService {

    UUID addUser(UserExtendedRequest newUser);

    UserResponse login(UserRequest loginRequest);

    UserEntity getUserByEmail(String email);
}
