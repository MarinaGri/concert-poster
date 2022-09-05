package com.technokratos.dto.response.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Exception info",
        description = "Contains key info about exception")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ExceptionMessage {

    @Schema(example = "User not found")
    private String message;

    @Schema(example = "UserNotFoundException")
    @JsonProperty("exception_name")
    private String exceptionName;

}
