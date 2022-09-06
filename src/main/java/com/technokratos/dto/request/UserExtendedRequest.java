package com.technokratos.dto.request;

import com.technokratos.dto.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Schema(name = "User extended request")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserExtendedRequest extends UserRequest {

    @Schema(name = "role", description = "User's role", example = "CUSTOMER")
    private Role role;

}
