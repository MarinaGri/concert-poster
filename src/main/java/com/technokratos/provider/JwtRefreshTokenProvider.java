package com.technokratos.provider;


import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.RefreshTokenEntity;

public interface JwtRefreshTokenProvider {

    RefreshTokenEntity generateRefreshToken(UserResponse accountResponse);

}
