package com.mynt.banking.currency_cloud.manage.accounts.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "CreateAccountRequest")
public class CreateAccountRequest {

    @JsonProperty("account_name")
    @NotNull
    @Size(max = 255)
    @Schema(description = "name of customer", example = "James Bond")
    @Builder.Default
    private String accountName = "James Bond";

    @JsonProperty("legal_entity_type")
    @NotNull
    @Size(max = 255)
    @Schema(description = "account type individual or company", example = "individual")
    @Builder.Default
    private String legalEntityType = "individual";

    @JsonProperty("street")
    @NotNull
    @Size(max = 255)
    @Schema(description = "street name", example = "The Mall")
    @Builder.Default
    private String street = "The Mall";

    @JsonProperty("city")
    @NotNull
    @Size(max = 255)
    @Schema(description = "city name", example = "London")
    @Builder.Default
    private String city = "London";

    @JsonProperty("country")
    @NotNull
    @Schema(description = "country name", example = "GB")
    @Size(max = 2)  // ISO 3166-1 alpha-2 country codes
    @Builder.Default
    private String country = "GB";

    @JsonProperty("postal_code")
    @NotNull
    @Size(max = 20)
    @Schema(description = "postcode", example = "SW1A 1AA")
    @Builder.Default
    private String postalCode = "SW1A 1AA";

    @JsonProperty("state_or_province")
    @Size(max = 255)
    private String stateOrProvince;

    @JsonProperty("brand")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "brand used internally to white lable accounts", example = "currencycloud")
    private String brand = "currencycloud";

    @JsonProperty("your_reference")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "User-generated reference code", example = "MyntFinance")
    private String yourReference = "MyntFinance";

    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "Account status", example = "enabled")
    private String status = "enabled";

    @JsonProperty("spread_table")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "The name of the spread table assigned to the account. This is needed if they intend to apply markup.", example = " ")
    private String spreadTable = "";

    @JsonProperty("identification_type")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "A legal document that verifies the identity of the account owner. Required for individual sub-accounts on our outsourced KYC model, optional otherwise. uses enums that can be found on cloud currency", example = "none")
    private String identificationType = "none";

    @JsonProperty("identification_value")
    @Size(max = 255)
    @Builder.Default
    @Schema(description = "Unique reference code for the identification document, such as a passport number. Required if \"identification_type\" is provided.", example = " ")
    private String identificationValue = "";

    @JsonProperty("api_trading")
    @Builder.Default
    @Schema(description = "Allows the account to make trades via the Currencycloud API.", example = "true")
    private Boolean apiTrading = true;

    @JsonProperty("online_trading")
    @Builder.Default
    @Schema(description = "Allows the account to make trades via Currencycloud Direct.", example = "true")
    private Boolean onlineTrading = true;

    @JsonProperty("phone_trading")
    @Builder.Default
    @Schema(description = "Allows the account to make trades via phone.", example = "true")
    private Boolean phoneTrading = true;

    @JsonProperty("terms_and_conditions_accepted")
    @Builder.Default
    @Schema(description = "Acceptance of the terms and conditions. Required for sub-accounts that are on our Outsourced KYC model, optional otherwise.", example = "true")
    private Boolean termsAndConditionsAccepted = true;
}





