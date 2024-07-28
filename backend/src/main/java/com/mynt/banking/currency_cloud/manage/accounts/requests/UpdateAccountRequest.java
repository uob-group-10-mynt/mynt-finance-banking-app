
package com.mynt.banking.currency_cloud.manage.accounts.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Updates a sub-account owned by the authenticated user. The updated account entity is returned on success.")
public class UpdateAccountRequest {

    //@NotNull
    @JsonProperty("account_name")
    @Size(max = 255)
    @Schema(description = "Account name.",
            example = " ")
    @Builder.Default
    private String accountName = "";

    //@NotNull
    @JsonProperty("legal_entity_type")
    @Size(max = 255)
    @Schema(description = "Legal entity.",
            example = " ")
    @Builder.Default
    private String legalEntityType = "";

    //@NotNull
    @JsonProperty("brand")
    @Size(max = 255)
    @Schema(description = "The value of this field is used for white labeling the Currencycloud user interface.",
            example = " ")
    @Builder.Default
    private String brand = "";

    //@NotNull
    @JsonProperty("your_reference")
    @Size(max = 255)
    @Schema(description = "User-generated reference code.",
            example = " ")
    @Builder.Default
    private String yourReference = "";

    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Schema(description = "Status of the account.",
            example = " ")
    @Builder.Default
    private String status = "";

    //@NotNull
    @JsonProperty("street")
    @Size(max = 255)
    @Schema(description = "First line of address.",
            example = " ")
    @Builder.Default
    private String street = "";

    //@NotNull
    @JsonProperty("city")
    @Size(max = 255)
    @Schema(description = "City name",
            example = " ")
    @Builder.Default
    private String city = "";

    //@NotNull
    @JsonProperty("state_or_province")
    @Size(max = 255)
    @Schema(description = "State or province two-letter ISO 3166 code. Only applicable to some countries.",
            example = " ")
    @Builder.Default
    private String stateOrProvince = "";

    //@NotNull
    @JsonProperty("postal_code")
    @Size(max = 255)
    @Schema(description = "Postal code.",
            example = " ")
    @Builder.Default
    private String postalCode = "";

    //@NotNull
    @JsonProperty("country")
    @Size(max = 255)
    @Schema(description = "Two-letter ISO country code.",
            example = " ")
    @Builder.Default
    private String country = "";

    //@NotNull
    @JsonProperty("spread_table")
    @Size(max = 255)
    @Schema(description = "The name of the spread table assigned to the account.",
            example = " ")
    @Builder.Default
    private String spreadTable = "";

    //@NotNull
    @JsonProperty("api_trading")
    @Size(max = 255)
    @Schema(description = "Whether trading via the Currencycloud API is to be enabled on the account.",
            example = " ")
    @Builder.Default
    private String apiTrading = "";

    //@NotNull
    @JsonProperty("online_trading")
    @Size(max = 255)
    @Schema(description = "Whether online trading is to be enabled on the account.",
            example = " ")
    @Builder.Default
    private String onlineTrading = "";

    //@NotNull
    @JsonProperty("phone_trading")
    @Size(max = 255)
    @Schema(description = "Whether phone trading is to be enabled on the account.",
            example = " ")
    @Builder.Default
    private String phoneTrading = "";

    //@NotNull
    @JsonProperty("identification_type")
    @Size(max = 255)
    @Schema(description = "A legal document that verifies the identity of the account owner.",
            example = " ")
    @Builder.Default
    private String identificationType = "";

    //@NotNull
    @JsonProperty("identification_value")
    @Size(max = 255)
    @Schema(description = "A unique reference code for the identification document, such as a passport number.",
            example = " ")
    @Builder.Default
    private String identificationValue = "";

    //@NotNull
    @JsonProperty("terms_and_conditions_accepted")
    @Size(max = 255)
    @Schema(description = "Acceptance of the terms and conditions.",
            example = " ")
    @Builder.Default
    private String termsAndConditionsAccepted = "";

}