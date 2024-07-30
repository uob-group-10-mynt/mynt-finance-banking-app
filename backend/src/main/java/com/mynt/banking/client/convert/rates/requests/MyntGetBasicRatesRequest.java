package com.mynt.banking.client.convert.rates.requests;

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
@Schema(description = "Gets indicative rates for selling a base currency for other currencies.")
public class MyntGetBasicRatesRequest {

    @NotNull
    @JsonProperty("base_currency")
    @Size(max = 255)
    @Schema(description = "The base currency.",
            example = "GBP")
    @Builder.Default
    private String baseCurrency = "";


    @JsonProperty("other_currencies")
    @Size(max = 255)
    @Schema(description = "The other currencies. " +
            "If left empty, returns a list for all available currencies. " +
            "To specify multiple currencies, use commas to separate.",
            example = "USD, KES")
    @Builder.Default
    private String otherCurrencies = "";

}
