package com.technokratos.controller;

import com.technokratos.api.JwtTokenApi;
import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JwtTokenController implements JwtTokenApi {

    private final JwtTokenService jwtTokenService;

    @Override
    public TokenCoupleResponse updateTokens(TokenCoupleRequest tokenCoupleRequest) {
        return jwtTokenService.refreshTokenCouple(tokenCoupleRequest);
    }
}
