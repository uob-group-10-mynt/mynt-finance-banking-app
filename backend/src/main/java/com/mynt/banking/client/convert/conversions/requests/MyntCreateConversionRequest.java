package com.mynt.banking.client.convert.conversions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Request for creating a conversion.")
public class MyntCreateConversionRequest {

    @NotBlank
    @JsonProperty("sell_currency")
    @Pattern(regexp = "^[A-z]{3}$", message = "Three-letter ISO code of currency")
    @Schema(description = "Three-letter ISO code of currency", example = "KES")
    private String sellCurrency;

    @NotBlank
    @JsonProperty("buy_currency")
    @Pattern(regexp = "^[A-z]{3}$", message = "Three-letter ISO code of currency")
    @Schema(description = "Three-letter ISO code of currency", example = "GBP")
    private String buyCurrency;

    @NotBlank
    @Pattern(regexp = "^[0-9]*(.[0-9]*)?$", message = "Incorrect format for amount")
    @Schema(description = "The amount to be converted", example = "0.00")
    private String amount;

    @NotBlank
    @JsonProperty("fixed_side")
    @Pattern(regexp = "^(buy|sell)$")
    @Schema(description = "Specify the fixed side of the conversion for the amount.", example = "buy")
    private String fixedSide;

}
