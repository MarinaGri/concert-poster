package com.technokratos.service.impl;

import com.technokratos.exception.token.TokenRefreshException;
import com.technokratos.model.RefreshTokenEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserRefreshTokenEntity;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.expiration.refresh.mills}")
    private long expirationRefreshInMills;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserRefreshTokenEntity generateRefreshToken(UserEntity user) {

        UserRefreshTokenEntity userRefreshToken = UserRefreshTokenEntity.builder()
                .expiryDate(Instant.now().plusMillis(expirationRefreshInMills))
                .user(user)
                .isDeleted(false)
                .build();

        return refreshTokenRepository.save(userRefreshToken);
    }

    @Transactional
    @Override
    public RefreshTokenEntity updateRefreshToken(String refreshToken) {
        UUID refreshTokenUuid = UUID.fromString(refreshToken);

        UserRefreshTokenEntity token = refreshTokenRepository.findById(refreshTokenUuid)
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "The token does not exist."));

        refreshTokenRepository.delete(token);

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(String.valueOf(token.getId()), "The renewal token has expired");
        }

        return generateRefreshToken(token.getUser());
    }
}
