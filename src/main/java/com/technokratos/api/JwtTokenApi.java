package com.technokratos.api;

import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "JWT Api")
@RequestMapping("/api/v1/token")
public interface JwtTokenApi {

    @Operation(summary = "Updating the previously issued access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New pair of access-refresh tokens",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TokenCoupleResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/refresh", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    TokenCoupleResponse updateTokens(@RequestBody TokenCoupleRequest tokenCouple);

    @Operation(summary = "Getting information about user by token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User' information",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserResponse.class)
                            )
                    }
            )
    })
    @SecurityRequirement(name = "Authorization")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UserResponse userInfoByToken(@Parameter(hidden = true) @RequestHeader("Authorization") String header);

}
