package com.mynt.banking.client.manage.balanaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyntFindBalanceRequest {

    @JsonProperty("currency_code")
    @Size(max = 3)
    @Schema(description = "The currency code to filter balances.", example = "USD")
    private String currencyCode;
}
