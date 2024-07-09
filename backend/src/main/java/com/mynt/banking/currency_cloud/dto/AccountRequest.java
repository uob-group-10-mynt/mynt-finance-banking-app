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

    @JsonProperty("postal_code")
    @NotNull
    @Size(max = 20)
    private String postalCode;

    @JsonProperty("country")
    @NotNull
    @Size(max = 2)  // ISO 3166-1 alpha-2 country codes
    private String country;

    @JsonProperty("state_or_province")
    @Size(max = 255)
    private String stateOrProvince;

    @JsonProperty("brand")
    @Size(max = 255)
    private String brand;

    @JsonProperty("your_reference")
    @Size(max = 255)
    private String yourReference;

    @JsonProperty("status")
    @Size(max = 255)
    private String status;

    @JsonProperty("spread_table")
    @Size(max = 255)
    private String spreadTable;

    @JsonProperty("identification_type")
    @Size(max = 255)
    private String identificationType;

    @JsonProperty("identification_value")
    @Size(max = 255)
    private String identificationValue;

    @JsonProperty("api_trading")
    private Boolean apiTrading;

    @JsonProperty("online_trading")
    private Boolean onlineTrading;

    @JsonProperty("phone_trading")
    private Boolean phoneTrading;

    @JsonProperty("terms_and_conditions_accepted")
    private Boolean termsAndConditionsAccepted;
}
