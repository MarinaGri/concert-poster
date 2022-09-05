package com.technokratos.util;

import com.technokratos.exception.token.AuthenticationHeaderException;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;

import static com.technokratos.consts.SecurityConst.BEARER;

@UtilityClass
public class AuthorizationHeaderUtil {

    public Optional<String> getTokenFromAuthorizationHeader(String authorizationHeader) {

        if (Objects.isNull(authorizationHeader)) {
            return Optional.empty();
        }

        if (!authorizationHeader.startsWith(BEARER)) {
            throw new AuthenticationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        Optional<String> token = HttpRequestUtil.getTokenFromAuthorizationHeader(authorizationHeader);
        if (token.isEmpty()) {
            throw new AuthenticationHeaderException("Authorization header token is empty");
        }

        return token;
    }

}
