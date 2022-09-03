package com.technokratos.provider;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.Map;

public interface JwtAccessTokenProvider {

    String generateAccessToken(String subject, Map<String, Object> data);

    Claims parseAccessToken(String accessToken);

    Date getExpirationDateFromAccessToken(String accessToken);

}

