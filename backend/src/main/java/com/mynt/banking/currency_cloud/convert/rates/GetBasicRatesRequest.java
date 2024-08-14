
package com.mynt.banking.currency_cloud.convert.rates;

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
@Schema(description = "Gets indicative foreign exchange rate information for one or more currency pairs in a single request.  This endpoint is intended to provide an indication of the rate available in the market and can support requests for multiple currency pairs, so is good for polling requirements such as live FX ticker widgets. This endpoint should be used where speed, availability and the ability to request multiple currency pairs is important.")
public class GetBasicRatesRequest {
    
    @NotNull
    @JsonProperty("currency_pair")
    @Size(max = 255)
    @Schema(description = "Concatenated string of the two currencies traded, e.g. \"USDEUR\". More than one pair can be entered, separated by a comma e.g. \"EURGBP,GBPUSD,EURUSD\".",
            example = " ")
    @Builder.Default
    private String currencyPair = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "A contact UUID for the sub-account you're acting on behalf of.",
            example = " ")
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("ignore_invalid_pairs")
    @Size(max = 255)
    @Schema(description = "Ignore the validation of currency pairs.",
            example = " ")
    @Builder.Default
    private String ignoreInvalidPairs = "";
    
}