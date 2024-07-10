package com.mynt.banking.currency_cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @JsonProperty("account_name")
    @NotNull
    private String accountName;

    @JsonProperty("legal_entity_type")
    @Builder.Default
    private String legalEntityType = "individual";

    @JsonProperty("street")
    @NotNull
    private String street;

    @JsonProperty("city")
    @NotNull
    private String city;

    @JsonProperty("postal_code")
    @NotNull
    private String postalCode;

    @JsonProperty("country")
    @NotNull
    private String country;

    @JsonProperty("state_or_province")
    private String stateOrProvince;

    @JsonProperty("brand")
    @Builder.Default
    private String brand = "currencycloud";

    @JsonProperty("your_reference")
    private String yourReference;

    @JsonProperty("status")
    @Builder.Default
    private String status = "enabled";

    @JsonProperty("spread_table")
    private String spreadTable;

    @JsonProperty("identification_type")
    private String identificationType;

    @JsonProperty("identification_value")
    private String identificationValue;

    @JsonProperty("api_trading")
    @Builder.Default
    private Boolean apiTrading = true;

    @JsonProperty("online_trading")
    @Builder.Default
    private Boolean onlineTrading = true;

    @JsonProperty("phone_trading")
    @Builder.Default
    private Boolean phoneTrading = true;

    @JsonProperty("terms_and_conditions_accepted")
    @Builder.Default
    private Boolean termsAndConditionsAccepted = true;
}
