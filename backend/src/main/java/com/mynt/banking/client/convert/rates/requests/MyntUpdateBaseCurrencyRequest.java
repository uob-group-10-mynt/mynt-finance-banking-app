package com.mynt.banking.client.convert.rates.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Update the user's base currency")
public class MyntUpdateBaseCurrencyRequest {

    @NotBlank
    @JsonProperty("new_base_currency")
    @Pattern(regexp = "^[A-z]{3}$", message = "Three-letter ISO code of currency")
    @Schema(description = "Three-letter ISO code of currency", example = "KES")
    private String newBaseCurrency;

}
