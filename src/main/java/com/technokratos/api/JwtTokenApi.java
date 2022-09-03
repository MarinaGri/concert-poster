package com.technokratos.api;

import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/token")
public interface JwtTokenApi {

    @PostMapping(value = "/refresh", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    TokenCoupleResponse updateTokens(@RequestBody TokenCoupleRequest tokenCouple);

}
