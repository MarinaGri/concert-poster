package com.technokratos.controller.handler;

import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.exception.CpServiceException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionMessage> onAllExceptions(Exception exception) {
        return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(CpServiceException.class)
    public final ResponseEntity<ExceptionMessage> onServiceExceptions(CpServiceException serviceException) {
        return createExceptionResponse(serviceException.getHttpStatus(), serviceException);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionMessage> onTypeMismatchExceptions(
            MethodArgumentTypeMismatchException exception) {
        return createExceptionResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(JwtException.class)
    public final ResponseEntity<ExceptionMessage> onJwtExceptions(JwtException jwtException) {
        return createExceptionResponse(HttpStatus.UNAUTHORIZED, jwtException);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionMessage>
            onAuthenticationExceptions(AuthenticationException authenticationException) {
        return createExceptionResponse(HttpStatus.UNAUTHORIZED, authenticationException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ExceptionMessage> onAccessDeniedExceptions(AccessDeniedException exception) {
        return createExceptionResponse(HttpStatus.FORBIDDEN, exception);
    }

    private ResponseEntity<ExceptionMessage> createExceptionResponse(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status)
                .body(ExceptionMessage.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());
    }

}
