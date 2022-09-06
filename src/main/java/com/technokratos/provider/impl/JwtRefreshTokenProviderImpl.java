package com.technokratos.provider.impl;

import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.provider.JwtRefreshTokenProvider;
import com.technokratos.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtRefreshTokenProviderImpl implements JwtRefreshTokenProvider {

    private final RefreshTokenService accountRefreshTokenService;

    @Override
    public RefreshTokenEntity generateRefreshToken(UserEntity user) {
        return accountRefreshTokenService.generateRefreshToken(user);
    }

    @Override
    public RefreshTokenEntity updateRefreshToken(String refreshToken) {
        return accountRefreshTokenService.updateRefreshToken(refreshToken);
    }

}
