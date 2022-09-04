package com.technokratos.service.impl;

import com.technokratos.dto.enums.Role;
import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.provider.JwtAccessTokenProvider;
import com.technokratos.provider.JwtRefreshTokenProvider;
import com.technokratos.service.JwtTokenService;
import com.technokratos.service.UserService;
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

    private final UserService userService;

    @Override
    public TokenCoupleResponse generateTokenCouple(UserResponse userResponse) {
        String accessToken = generateAccessToken(userResponse.getEmail(), userResponse.getRole());

        UserEntity user = userService.getUserByEmail(userResponse.getEmail());
        RefreshTokenEntity refreshToken = jwtRefreshTokenProvider.generateRefreshToken(user);

        return generateTokenCouple(accessToken, refreshToken);
    }

    @Override
    public TokenCoupleResponse refreshTokenCouple(TokenCoupleRequest tokenCoupleRequest) {
        RefreshTokenEntity newRefreshToken = jwtRefreshTokenProvider.updateRefreshToken(
                tokenCoupleRequest.getRefreshToken());

        String oldAccessToken = tokenCoupleRequest.getAccessToken();

        String subject = jwtAccessTokenProvider.getSubjectFromAccessToken(oldAccessToken);
        Role role = jwtAccessTokenProvider.getRoleFromAccessToken(oldAccessToken);
        String newAccessToken = generateAccessToken(subject, role);

        return generateTokenCouple(newAccessToken, newRefreshToken);
    }

    @Override
    public UserEntity getUserByToken(String token) {
        return jwtAccessTokenProvider.getUserByToken(token);
    }

    private String generateAccessToken(String subject, Role role) {
        return jwtAccessTokenProvider.generateAccessToken(
                subject,
                Collections.singletonMap(ROLE, role)
        );
    }

    private TokenCoupleResponse generateTokenCouple(String accessToken, RefreshTokenEntity refreshToken) {
        Date expirationDate = jwtAccessTokenProvider.getExpirationDateFromAccessToken(accessToken);

        return TokenCoupleResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpirationDate(expirationDate)
                .refreshToken(refreshToken.getId().toString())
                .refreshTokenExpirationDate(Date.from(refreshToken.getExpiryDate()))
                .build();
    }
}
