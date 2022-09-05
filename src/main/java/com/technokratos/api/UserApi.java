package com.technokratos.api;

import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.message.ExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "User Api")
@RequestMapping("/api/v1/user")
public interface UserApi {

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ID of the registered user",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed or user already exists",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID addUser(@RequestBody UserExtendedRequest newUser);

    @Operation(summary = "Getting tokens based on registered data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pair of access-refresh tokens",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TokenCoupleResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found with email or password",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
    })
    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    TokenCoupleResponse login(@RequestBody UserRequest loginRequest);

}
