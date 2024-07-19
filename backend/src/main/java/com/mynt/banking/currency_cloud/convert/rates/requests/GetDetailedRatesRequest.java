package com.mynt.banking.currency_cloud.convert.rates.requests;

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
@Schema(description = "GetDetailedRatesRequest")
public class GetDetailedRatesRequest {

    @JsonProperty("buy_currency")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Currency purchased - three-letter ISO code.", example = "USD")
    private String buyCurrency;

    @JsonProperty("sell_currency")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Currency sold - three-letter ISO code.", example = "EUR")
    private String sellCurrency;

    @JsonProperty("fixed_side")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Fix the buy or sell currency.\n" +
            "\n" +
            "Enum\n" +
            "buy\n" +
            "sell", example = "buy")
    private String fixedSide;

    @JsonProperty("amount")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Amount of the fixed buy or sell currency.", example = "1000")
    private String amount;

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("conversion_date")
    @Size(max = 255)
    @Schema(description = "Earliest delivery date in UTC time zone. Format YYYY-MM-DD.", example = " ")
    @Builder.Default
    private Date conversionDate = null;

    @JsonProperty("conversion_date_preference")
    @Size(max = 255)
    @Schema(description = "Available and required only if conversion_date is not provided. Not currently supported for APAC currencies.\n" +
            "Must be one of the following:\n" +
            "- 'earliest' for earliest available conversion date. Make sure there is sufficient time to send funds to Currencycloud.\n" +
            "- 'next_day' for next day available conversion date - T+1.\n" +
            "- 'default' for conversion - T+1 for APAC restricted currencies, T+2 for all others.\n" +
            "- 'optimize_liquidity' for maximizing chances of getting a successful rate. Most relevant for exotic pairs. " +
            "Conversion is T+0 for APAC restricted currencies, and T+1 or T+2 for all others.\n" +
            "\n" +
            "Enum\n" +
            "earliest\n" +
            "next_day\n" +
            "default\n" +
            "optimize_liquidity", example = " ")
    @Builder.Default
    private String conversionDatePreference = "";
}
