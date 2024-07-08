package com.mynt.banking.currency_cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    @JsonProperty("auth_token")
    private String authToken;
}
