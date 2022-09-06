package com.technokratos.service.impl;

import com.technokratos.dto.enums.Role;
import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.exception.token.AuthenticationHeaderException;
import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.provider.JwtAccessTokenProvider;
import com.technokratos.provider.JwtRefreshTokenProvider;
import com.technokratos.service.JwtTokenService;
import com.technokratos.service.UserService;
import com.technokratos.util.AuthorizationHeaderUtil;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static com.technokratos.consts.SecurityConst.ROLE;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public TokenCoupleResponse generateTokenCouple(UserResponse userResponse) {
        String accessToken = generateAccessToken(userResponse.getEmail(), userResponse.getRole());

        UserEntity user = userService.getUserByEmail(userResponse.getEmail());
        RefreshTokenEntity refreshToken = jwtRefreshTokenProvider.generateRefreshToken(user);

        return generateTokenCouple(accessToken, refreshToken);
    }

    @Override
    @Transactional
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
    @Transactional
    public UserEntity getUserByToken(String token) {
        return jwtAccessTokenProvider.getUserByToken(token);
    }

    @Override
    @Transactional
    public UserResponse getUserInfoByHeader(String header) {
        Optional<String> token = AuthorizationHeaderUtil.getTokenFromAuthorizationHeader(header);
        if (token.isEmpty()) {
            throw new AuthenticationHeaderException("Header value not valid");
        }
        return userMapper.toResponse(getUserByToken(token.get()));
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
