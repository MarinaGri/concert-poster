package com.technokratos.util;

import com.technokratos.exception.token.AuthenticationHeaderException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.technokratos.consts.JwtConst.ACCESS_TOKEN;
import static com.technokratos.consts.SecurityConst.BEARER;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testGetTokenFromAuthHeader() {
        String header = BEARER.concat(StringUtils.SPACE).concat(ACCESS_TOKEN);
        Optional<String> actualResponse = HttpRequestUtil.getTokenFromAuthorizationHeader(header);
        assertEquals(Optional.of(ACCESS_TOKEN), actualResponse);
    }

    @Test
    void testGetTokenFromWrongAuthHeader() {
        assertThrows(AuthenticationHeaderException.class,
                () -> AuthorizationHeaderUtil.getTokenFromAuthorizationHeader(""));
    }

    @Test
    void testGetTokenFromEmptyAuthHeader() {
        Optional<String> actualResponse = AuthorizationHeaderUtil.getTokenFromAuthorizationHeader(null);
        assertEquals(Optional.empty(), actualResponse);
    }
}
