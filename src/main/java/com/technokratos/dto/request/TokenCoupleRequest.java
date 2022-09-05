package com.technokratos.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(name = "User's pair access-refresh tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenCoupleRequest {

    @Schema(description = "User's access token", example = "eyJhbGciOiJIUzUxMiJ9." +
            "eyJST0xFIjoiVVNFUiIsInN1YiI6Im1hcmluYUBnbWFpbC5jb20iLCJleHAiOjE2NTc4Mjc3MTMsImlhdCI6MTY1NzgyNzExM30." +
            "FLs2GdjxWQAbsm9IUdPYC5XilLsHrHRA7dWUJxB2rxidbGCY1IIJiswsnXSr_87ftPQvNm3OLSvkaF6Zzf52Zg")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "User's refresh token", example = "60f70556-312c-47e0-a7c5-09b6f93b3344")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
