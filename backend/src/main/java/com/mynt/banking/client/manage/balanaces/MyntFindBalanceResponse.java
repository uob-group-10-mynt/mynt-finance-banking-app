package com.mynt.banking.client.manage.balanaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyntFindBalanceResponse {

    @JsonProperty("bank")
    @Schema(description = "The name of the bank.", example = "mynt")
    private String bank;

    @JsonProperty("account_label")
    @Schema(description = "The label of the account.", example = "USD Current Account")
    private String label;

    @JsonProperty("account_number_type")
    @Schema(description = "The type of account number.", example = "iban")
    private String accountNumberType;

    @JsonProperty("account_number")
    @Schema(description = "The account number.", example = "GB88TCCL12345674642170")
    private String accountNumber;

    @JsonProperty("routing_code")
    @Schema(description = "The routing code.", example = "TCCLGB3L")
    private String routingCode;

    @JsonProperty("routing_code_type")
    @Schema(description = "The type of routing code.", example = "bic_swift")
    private String routingCodeType;

    @JsonProperty("balance")
    @Schema(description = "The balance of the account.", example = "1010234.0")
    private String balance;

    @JsonProperty("currency")
    @Schema(description = "The currency of the balance.", example = "USD")
    private String currency;
}
