package com.mynt.banking.mPesa.requests;

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
public class SendMpesaDto {

    @JsonProperty("debit_currency")
    @Size(max = 255)
    @Schema(description = "debit_currency", example = "KES")
    @Builder.Default
    private String debitCurrency = "KES" ;

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

    @NotNull
    @JsonProperty("mobile_number")
    @Size(max = 255)
    @Schema(description = "mobile_number", example = "07824792472")
    @Builder.Default
    private String mobileNumber = "07824792472" ;

    @NotNull
    @JsonProperty("sender")
    @Size(max = 255)
    @Schema(description = "sender", example = "James Love")
    @Builder.Default
    private String sender = "James Love";

    @NotNull
    @JsonProperty("beneficiary_name")
    @Size(max = 255)
    @Schema(description = "beneficiary_name", example = "James Love")
    @Builder.Default
    private String beneficiaryName = "James Love";

    @JsonProperty("sender_country")
    @Size(max = 255)
    @Schema(description = "sender_country", example = "KE")
    @Builder.Default
    private String senderCountry = "KE";

}

