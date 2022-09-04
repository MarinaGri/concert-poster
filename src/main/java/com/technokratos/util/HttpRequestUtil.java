package com.technokratos.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static com.technokratos.consts.SecurityConstants.BEARER;

@UtilityClass
public class HttpRequestUtil {

    public Optional<String> getTokenFromAuthorizationHeader(String authorizationHeader) {
        if (Objects.isNull(authorizationHeader)) {
            return Optional.empty();
        }
        String token = authorizationHeader
                .replace(BEARER.concat(StringUtils.SPACE), StringUtils.EMPTY);
        return Optional.of(token);
    }
}
