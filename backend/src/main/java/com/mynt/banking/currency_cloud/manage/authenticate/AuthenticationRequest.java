package com.mynt.banking.currency_cloud.manage.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the API request to the /v2/authenticate/api endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("api_key")
    private String apiKey;
}
