package com.technokratos.service;

import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;

public interface JwtTokenService {

    TokenCoupleResponse generateTokenCouple(UserResponse userResponse);

}
