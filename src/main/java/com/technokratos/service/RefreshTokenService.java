package com.technokratos.service;

import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;

public interface RefreshTokenService {

    RefreshTokenEntity generateRefreshToken(UserEntity user);

    RefreshTokenEntity updateRefreshToken(String refreshToken);

}
