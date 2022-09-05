package com.technokratos.provider.impl;

import com.technokratos.dto.enums.Role;
import com.technokratos.exception.token.AuthenticationHeaderException;
import com.technokratos.model.UserEntity;
import com.technokratos.provider.JwtAccessTokenProvider;
import com.technokratos.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.technokratos.consts.SecurityConst.ROLE;

@RequiredArgsConstructor
@Component
public class JwtAccessTokenProviderImpl implements JwtAccessTokenProvider {

    @Value("${jwt.expiration.access.mills}")
    private long expirationAccessInMills;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserService userService;

    @Override
    public String generateAccessToken(String subject, Map<String, Object> data) {
        Map<String, Object> claims = new HashMap<>(data);
        claims.put(Claims.SUBJECT, subject);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationAccessInMills)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public Claims parseAccessToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(accessToken)
                .getBody();
    }

    @Override
    public UserEntity getUserByToken(String token) {
        try {
            Claims claims = parseAccessToken(token);
            String subject = claims.getSubject();
            return userService.getUserByEmail(subject);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationHeaderException("Token expired date error");
        }
    }

    @Override
    public Date getExpirationDateFromAccessToken(String accessToken) {
        try {
            return parseAccessToken(accessToken).getExpiration();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration();
        }
    }

    @Override
    public Role getRoleFromAccessToken(String accessToken) {
        try {
            return Role.valueOf((String) parseAccessToken(accessToken).get(ROLE));
        } catch (ExpiredJwtException e) {
            return Role.valueOf((String) e.getClaims().get(ROLE));
        }
    }

    @Override
    public String getSubjectFromAccessToken(String accessToken) {
        try {
            return parseAccessToken(accessToken).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }
}
