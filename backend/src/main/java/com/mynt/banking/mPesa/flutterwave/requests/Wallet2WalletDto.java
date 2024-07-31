package com.mynt.banking.mPesa.flutterwave.requests;

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
public class Wallet2WalletDto {

    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Schema(description = "Amount", example = "1")
    @Builder.Default
    private Integer amount = 1 ;

    @NotNull
    @JsonProperty("email")
    @Size(max = 255)
    @Schema(description = "email", example = "james@jameslove.com")
    @Builder.Default
    private String email = "james@jameslove.com" ;

}
