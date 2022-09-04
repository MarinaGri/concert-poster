package com.technokratos.consts;

import com.technokratos.dto.enums.Role;
import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;

import java.util.UUID;

public class UserConst {

    public static final UUID USER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
    public static final UUID ADMIN_ID = UUID.fromString("123e4567-e89b-12d3-a456-426655440001");

    public static final String USER_EMAIL = "some_login@gmail.com";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String USER_REPEATED_EMAIL = "repeated_email@gmail.com";
    public static final String USER_WRONG_EMAIL = "wrong_email@gmail.com";

    public static final Role USER_ROLE = Role.CUSTOMER;

    public static final String HASH_PASSWORD = "some-hash";
    public static final String USER_PASSWORD = "pa$swo4d!";
    public static final String USER_WRONG_PASSWORD = "wrong-pa$swo4d!";

    public static final UserExtendedRequest USER_REQUEST = UserExtendedRequest.builder()
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .password(USER_PASSWORD)
            .build();

    public static final UserRequest LOGIN_REQUEST = UserRequest.builder()
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .build();

    public static final UserRequest LOGIN_REQUEST_WRONG_EMAIL = UserRequest.builder()
            .email(USER_WRONG_EMAIL)
            .password(USER_PASSWORD)
            .build();

    public static final UserRequest LOGIN_REQUEST_WRONG_PASSWORD = UserRequest.builder()
            .email(USER_EMAIL)
            .password(USER_WRONG_PASSWORD)
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

    public static final UserEntity REPEATED_USER_ENTITY = UserEntity.builder()
            .email(USER_REPEATED_EMAIL)
            .role(USER_ROLE)
            .hashPassword(HASH_PASSWORD)
            .build();

    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .hashPassword(HASH_PASSWORD)
            .build();

    public static final UserResponse USER_RESPONSE = UserResponse.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .role(USER_ROLE)
            .build();

    public static final UserResponse ADMIN_RESPONSE = UserResponse.builder()
            .id(ADMIN_ID)
            .email(ADMIN_EMAIL)
            .role(Role.ADMIN)
            .build();
}
