package com.technokratos.dto.response;

import com.technokratos.dto.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Schema(name = "User info", description = "Information about user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    @Schema(name = "id", description = "Identifier", example = "123e4567-e89b-12d3-a456-426655440000")
    private UUID id;

    @Schema(name = "email", description = "User's email", example = "marina@gmail.com")
    private String email;

    @Schema(name = "role", description = "User's role", example = "CUSTOMER")
    private Role role;

}
