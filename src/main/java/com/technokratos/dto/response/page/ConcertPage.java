package com.technokratos.dto.response.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technokratos.dto.response.ConcertResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name = "Concerts page", description = "Page of concerts and total number of such pages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcertPage {

    @Schema(name = "concerts", description = "List of concerts")
    @JsonProperty("concerts")
    private List<ConcertResponse> concerts;

    @Schema(name = "current_page", description = "Index of current page", example = "0")
    @JsonProperty("current_page")
    private Integer currentPage;

    @Schema(name = "total_pages", description = "Total number of pages", example = "5")
    @JsonProperty("total_pages")
    private Integer totalPages;

}
