package com.technokratos.consts;

import com.technokratos.dto.enums.Role;
import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.model.UserEntity;

import java.util.UUID;

public class UserConst {

    public static final UUID USER_ID = UUID.randomUUID();

    public static final String USER_EMAIL = "some_login@gmail.com";

    public static final String USER_REPEATED_EMAIL = "repeated_email@gmail.com";

    public static final Role USER_ROLE = Role.CUSTOMER;

    public static final String HASH_PASSWORD = "some-hash";

    public static final String USER_PASSWORD = "pa$swo4d!";

    public static final UserExtendedRequest USER_REQUEST = UserExtendedRequest.builder()
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .password(USER_PASSWORD)
            .build();

    public static final UserExtendedRequest REPEATED_USER_REQUEST = UserExtendedRequest.builder()
            .email(USER_REPEATED_EMAIL)
            .role(USER_ROLE)
            .password(USER_PASSWORD)
            .build();

    public static final UserEntity NEW_USER_ENTITY = UserEntity.builder()
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .hashPassword(HASH_PASSWORD)
            .build();

    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .hashPassword(HASH_PASSWORD)
            .build();
}
