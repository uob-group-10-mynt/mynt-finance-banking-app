package com.mynt.banking.currency_cloud.pay.transfers.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateTransferRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateTransferRequest {

    @JsonProperty("source_account_id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Account UUID of the paying account.", example = " ")
    private String sourceAccountId;

    @JsonProperty("destination_account_id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Account UUID of the receiving account.", example = " ")
    private String destinationAccountId;

    @JsonProperty("currency")
    @NotNull
    @Size(min = 3, max = 3)
    @Schema(description = "Three-letter ISO currency code.", example = " ")
    private String currency;

    @JsonProperty("amount")
    @NotNull
    @Schema(description = "Amount", example = " ")
    private double amount;

    @JsonProperty("reason")
    @Size(max = 255)
    @Schema(description = "User-generated reason for transfer, freeform text.", example = " ")
    @Builder.Default
    private String reason = "";

    @JsonProperty("unique_request_id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "User-generated idempotency key. The value must be 100 characters or fewer.", example = " ")
    @Builder.Default
    private String uniqueRequestId = "";
}
