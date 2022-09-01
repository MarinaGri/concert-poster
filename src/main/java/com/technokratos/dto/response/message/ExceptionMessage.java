package com.technokratos.dto.response.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ExceptionMessage {

    private String message;

    @JsonProperty("exception_name")
    private String exceptionName;

}
