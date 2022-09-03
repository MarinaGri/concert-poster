package com.technokratos.service;


import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.RefreshTokenEntity;

public interface RefreshTokenService {

    RefreshTokenEntity generateRefreshToken(UserResponse userResponse);

}
