package com.technokratos.provider;

import com.technokratos.dto.enums.Role;
import com.technokratos.model.UserEntity;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.Map;

public interface JwtAccessTokenProvider {

    String generateAccessToken(String subject, Map<String, Object> data);

    Claims parseAccessToken(String accessToken);

    Role getRoleFromAccessToken(String accessToken);

    UserEntity getUserByToken(String token);

    Date getExpirationDateFromAccessToken(String accessToken);

    String getSubjectFromAccessToken(String accessToken);
}
