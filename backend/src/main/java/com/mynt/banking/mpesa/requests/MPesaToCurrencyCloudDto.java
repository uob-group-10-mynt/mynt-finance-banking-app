package com.mynt.banking.mpesa.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "CreateTransactionRequest")
@Builder
public class MPesaToCurrencyCloudDto {

    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Schema(description = "Amount", example = "1")
    @Builder.Default
    private String amount = "1";

}

