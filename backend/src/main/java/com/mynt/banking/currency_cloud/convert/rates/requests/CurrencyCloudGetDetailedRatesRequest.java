
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
public class CurrencyCloudGetDetailedRatesRequest {
    
    @NotNull
    @JsonProperty("buy_currency")
    @Size(max = 255)
    @Builder.Default
    private String buyCurrency = "";
    
    @NotNull
    @JsonProperty("sell_currency")
    @Size(max = 255)
    @Builder.Default
    private String sellCurrency = "";
    
    @NotNull
    @JsonProperty("fixed_side")
    @Size(max = 255)
    @Builder.Default
    private String fixedSide = "";
    
    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Builder.Default
    private String amount = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("conversion_date")
    @Size(max = 255)
    @Builder.Default
    private String conversionDate = "";
    
    //@NotNull
    @JsonProperty("conversion_date_preference")
    @Size(max = 255)
    @Builder.Default
    private String conversionDatePreference = "";
    
}