package com.technokratos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Concert request", description = "Information about concert")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertRequest {

    @Schema(name = "name", description = "Concert's name", example = "Some name")
    @JsonProperty("name")
    private String name;

    @Schema(name = "description", description = "Concert's description", example =
            "A concert is a live music performance in front of an audience." +
            " The performance may be by a single musician, sometimes then called a recital...")
    @JsonProperty("description")
    private String description;

    @Schema(name = "tickets_number", description = "Concert's tickets number", example = "42")
    @JsonProperty("tickets_number")
    private Integer ticketsNumber;

}
