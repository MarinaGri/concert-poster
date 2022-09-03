package com.technokratos.service.impl;

import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserRefreshTokenEntity;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.service.RefreshTokenService;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.expiration.refresh.mills}")
    private long expirationRefreshInMills;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    @Transactional
    @Override
    public UserRefreshTokenEntity generateRefreshToken(UserResponse user) {

        UserRefreshTokenEntity userRefreshToken = UserRefreshTokenEntity.builder()
                .expiryDate(Instant.now().plusMillis(expirationRefreshInMills))
                .user(userService.getUserByEmail(user.getEmail()))
                .isDeleted(false)
                .build();

        return refreshTokenRepository.save(userRefreshToken);
    }
}
