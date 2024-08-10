
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
public class CurrencyCloudGetBasicRatesRequest {
    
    @NotNull
    @JsonProperty("currency_pair")
    @Size(max = 255)
    @Builder.Default
    private String currencyPair = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("ignore_invalid_pairs")
    @Size(max = 255)
    @Builder.Default
    private String ignoreInvalidPairs = "";
    
}