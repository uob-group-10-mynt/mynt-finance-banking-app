package com.mynt.banking.currency_cloud.manage.reference.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Returns the information that is required to make payments to beneficiaries in specified currencies and countries.")
public class GetBeneficiaryRequirementsRequest {

    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Schema(description = "Currency in which money is held in the beneficiary's bank account. Three-letter ISO currency code.",
            example = "gbp")
    @Builder.Default
    private String currency = "gbp";

    @NotNull
    @JsonProperty("bank_account_country")
    @Size(max = 255)
    @Schema(description = "Two-letter ISO country code.",
            example = "gb")
    @Builder.Default
    private String bankAccountCountry = "gb";

    @NotNull
    @JsonProperty("beneficiary_country")
    @Size(max = 255)
    @Schema(description = "Two-letter ISO country code. If the beneficiary is a company, this is the country in which the company is registered. If the beneficiary is an individual, this is the country in which they are based.",
            example = "gb")
    @Builder.Default
    private String beneficiaryCountry = "gb";

}