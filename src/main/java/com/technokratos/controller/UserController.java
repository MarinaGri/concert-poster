package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.service.JwtTokenService;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;

    @Override
    public UUID addUser(UserExtendedRequest newUser) {
        return userService.addUser(newUser);
    }

    @Override
    public TokenCoupleResponse login(UserRequest loginRequest) {
        return jwtTokenService.generateTokenCouple(userService.login(loginRequest));
    }
}
