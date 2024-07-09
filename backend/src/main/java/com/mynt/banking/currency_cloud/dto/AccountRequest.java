package com.mynt.banking.currency_cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class AccountRequest {

    @JsonProperty("account_name")
    @NotNull
    @Size(max = 255)
    private String accountName;

    @JsonProperty("legal_entity_type")
    @NotNull
    @Size(max = 255)
    private String legalEntityType;

    @JsonProperty("street")
    @NotNull
    @Size(max = 255)
    private String street;

    @JsonProperty("city")
    @NotNull
    @Size(max = 255)
    private String city;

    @JsonProperty("country")
    @NotNull
    @Size(max = 2)  // ISO 3166-1 alpha-2 country codes
    private String country;

}
