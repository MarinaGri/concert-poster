package com.technokratos.consts;

import com.technokratos.dto.response.message.ExceptionMessage;

import static com.technokratos.consts.UserConst.*;

public class ApiConst {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found with email: ";

    public static final ExceptionMessage USER_ALREADY_EXISTS_MESSAGE = ExceptionMessage.builder()
            .message("User already exists with email: " + USER_REPEATED_EMAIL)
            .exceptionName("UserAlreadyExistsException")
            .build();

    public static final ExceptionMessage WRONG_EMAIL_MESSAGE = ExceptionMessage.builder()
            .message(USER_NOT_FOUND_MESSAGE + USER_WRONG_EMAIL)
            .exceptionName("UserNotFoundException")
            .build();

    public static final ExceptionMessage WRONG_PASSWORD_MESSAGE = ExceptionMessage.builder()
            .message(USER_NOT_FOUND_MESSAGE + USER_EMAIL)
            .exceptionName("UserNotFoundException")
            .build();

}
