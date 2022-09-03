package com.technokratos.service.impl;

import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.provider.JwtAccessTokenProvider;
import com.technokratos.provider.JwtRefreshTokenProvider;
import com.technokratos.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

import static com.technokratos.consts.SecurityConstants.ROLE;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    @Override
    public TokenCoupleResponse generateTokenCouple(UserResponse userResponse) {
        String accessToken = jwtAccessTokenProvider.generateAccessToken(
                userResponse.getEmail(),
                Collections.singletonMap(ROLE, userResponse.getRole())
        );

        RefreshTokenEntity refreshToken = jwtRefreshTokenProvider.generateRefreshToken(userResponse);
        Date expirationDate = jwtAccessTokenProvider.getExpirationDateFromAccessToken(accessToken);
        return TokenCoupleResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getId().toString())
                .accessTokenExpirationDate(expirationDate)
                .refreshTokenExpirationDate(Date.from(refreshToken.getExpiryDate()))
                .build();
    }
}
