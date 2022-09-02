package com.technokratos.service;

import com.technokratos.dto.request.UserRequest;

import java.util.UUID;

public interface UserService {

    UUID addUser(UserRequest newUser);

}
