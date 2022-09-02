package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UUID addUser(UserRequest newUser) {
        return userService.addUser(newUser);
    }
}
