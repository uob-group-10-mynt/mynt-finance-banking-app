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
public class CloudCurrency2MpesaDto {

    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Schema(description = "Amount", example = "1")
    @Builder.Default
    private Integer amount = 1 ;

    @NotNull
    @JsonProperty("mobile_number")
    @Size(max = 255)
    @Schema(description = "mobile_number", example = "0447824792472")
    @Builder.Default
    private String mobileNumber = "0447824792472" ;

    @JsonProperty("beneficiary_name")
    @Size(max = 255)
    @Schema(description = "beneficiary_name", example = "0447824792472")
    @Builder.Default
    private String beneficiaryName = "Flutterwave Developers" ;
}
