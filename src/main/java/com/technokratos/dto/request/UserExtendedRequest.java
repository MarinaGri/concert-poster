package com.technokratos.dto.request;

import com.technokratos.dto.enums.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserExtendedRequest extends UserRequest {

    private Role role;

}
