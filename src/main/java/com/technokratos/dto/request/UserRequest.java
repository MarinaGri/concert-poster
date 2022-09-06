package com.technokratos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(name = "User request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRequest {

    @Schema(name = "email", description = "User's email", example = "marina@gmail.com")
    private String email;

    @Schema(name = "password", description = "User's password", example = "pas$word")
    private String password;

}
