package com.technokratos.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(name = "Info about new pair access-refresh tokens")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenCoupleResponse {

    @Schema(description = "User's access token", example = "eyJhbGciOiJIUzUxMiJ9." +
            "eyJST0xFIjoiVVNFUiIsInN1YiI6Im1hcmluYUBnbWFpbC5jb20iLCJleHAiOjE2NTc4Mjc3MTMsImlhdCI6MTY1NzgyNzExM30." +
            "FLs2GdjxWQAbsm9IUdPYC5XilLsHrHRA7dWUJxB2rxidbGCY1IIJiswsnXSr_87ftPQvNm3OLSvkaF6Zzf52Zg")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "User's refresh token", example = "60f70556-312c-47e0-a7c5-09b6f93b3344")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(description = "Date when the access token will expire", example = "2022-07-14T19:41:53.000+00:00")
    @JsonProperty("access_token_expiration_date")
    private Date accessTokenExpirationDate;

    @Schema(description = "Date when the refresh token will expire", example = "2022-07-14T19:41:53.000+00:00")
    @JsonProperty("refresh_token_expiration_date")
    private Date refreshTokenExpirationDate;
}
