package com.technokratos.dto.response.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technokratos.dto.response.ConcertResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcertPage {

    @JsonProperty("concerts")
    private List<ConcertResponse> concerts;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("total_pages")
    private Integer totalPages;

}
