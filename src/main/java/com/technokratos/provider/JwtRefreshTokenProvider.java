package com.technokratos.provider;


import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;

public interface JwtRefreshTokenProvider {

    RefreshTokenEntity generateRefreshToken(UserEntity user);

    RefreshTokenEntity updateRefreshToken(String refreshToken);
}
