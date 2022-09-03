package com.technokratos.provider.impl;

import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.provider.JwtRefreshTokenProvider;
import com.technokratos.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtRefreshTokenProviderImpl implements JwtRefreshTokenProvider {

    private final RefreshTokenService accountRefreshTokenService;

    @Override
    public RefreshTokenEntity generateRefreshToken(UserResponse userResponse) {
        return accountRefreshTokenService.generateRefreshToken(userResponse);
    }

}
