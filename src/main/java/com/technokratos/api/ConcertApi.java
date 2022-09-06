package com.technokratos.api;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.dto.response.page.ConcertPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "Concert Api")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/v1/concert")
public interface ConcertApi {

    @Operation(summary = "Adding new concert information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added concert info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            )
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ConcertResponse createConcert(@RequestBody ConcertRequest newConcert);

    @Operation(summary = "Getting information about concert by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert info",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Concert's id cannot be converted to UUID",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Concert not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            )
    })
    @GetMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse getConcertById(@PathVariable("concert-id") UUID concertId);

    @Operation(summary = "Getting concerts with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts info",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertPage.class))
                    }
            )
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertPage getConcertPage(Pageable pageable);

    @Operation(summary = "Updating existing concert information by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated concert info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Concert's id cannot be converted to UUID",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Concert not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            )
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{concert-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse updateConcertById(@PathVariable("concert-id") UUID concertId,
                                      @RequestBody ConcertRequest updatedConcert);

    @Operation(summary = "Deletion existing concert information by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert has been deleted",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Concert's id cannot be converted to UUID",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Concert not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            )
    })
    @DeleteMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteConcertById(@PathVariable("concert-id") UUID concertId);

    @Operation(summary = "Booking a concert ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information about the buyer is recorded, " +
                    "the number of tickets is updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ConcertResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Concert's id cannot be converted to UUID or tickets are sold out",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Concert not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/{concert-id}/user", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void addBookingInfo(@PathVariable("concert-id") UUID concertId,
                        @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") UUID userId);
}
