package com.mynt.banking.currency_cloud.convert.conversions.requests;

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
@Schema(description = "CreateTransactionRequest")
public class CreateConversionRequest {

    @JsonProperty("buy_currency")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Three-character ISO code for currency purchased.", example = "USD")
    private String buyCurrency;

    @JsonProperty("sell_currency")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Three-character ISO code for currency sold.", example = "EUR")
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
    @Schema(description = "Amount of the fixed buy or sell currency.", example = " ")
    private String amount;

    @JsonProperty("term_agreement")
    @NotNull
    @Schema(description = "Indicates agreement to terms and conditions.", example = "true")
    private Boolean termAgreement;

    @JsonProperty("conversion_date")
    @Size(max = 255)
    @Schema(description = "Earliest delivery date in UTC time zone. Format YYYY-MM-DD.", example = " ")
    @Builder.Default
    private String conversionDate = "";

    @JsonProperty("client_buy_amount")
    @Size(max = 255)
    @Schema(description = "Client buy amount.", example = " ")
    @Builder.Default
    private String clientBuyAmount = "";

    @JsonProperty("client_sell_amount")
    @Size(max = 255)
    @Schema(description = "Client sell amount.", example = " ")
    @Builder.Default
    private String clientSellAmount = "";

    @JsonProperty("reason")
    @Size(max = 255)
    @Schema(description = "User-generated reason for conversion - freeform text.", example = " ")
    @Builder.Default
    private String reason = "";

    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Schema(description = "User-generated idempotency key. Beneficial for request tracking / management.", example = " ")
    @Builder.Default
    private String uniqueRequestId = "";

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

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

