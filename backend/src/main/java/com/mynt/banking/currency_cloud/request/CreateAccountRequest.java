package com.mynt.banking.currency_cloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequest {
    @NotNull
    @JsonProperty("account_name")
    private String accountName;

    @NotNull
    @JsonProperty("legal_entity_type")
    private String legalEntityType;

    @NotNull
    @JsonProperty("street")
    private String street;

    @NotNull
    @JsonProperty("city")
    private String city;

    @NotNull
    @JsonProperty("country")
    private String country;

    @NotNull
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("your_reference")
    private String yourReference;

    @JsonProperty("status")
    private String status;

    @JsonProperty("state_or_province")
    private String stateOrProvince;

    @JsonProperty("identification_type")
    private String identificationType;

    @JsonProperty("identification_value")
    private String identificationValue;
}
