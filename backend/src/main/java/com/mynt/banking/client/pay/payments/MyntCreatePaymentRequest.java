package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyntCreatePaymentRequest {

    @NotBlank
    @JsonProperty("beneficiary_id")
    @Pattern(regexp = "^([A-z0-9]-?){1,100}$", message = "Incorrect format for beneficiary id")
    @Schema(description = "The beneficiary's id", example = "73fd21b9-f260-40f5-8a3f-5a76982aba9b")
    private String beneficiaryId;

    @NotBlank
    @Pattern(regexp = "^[0-9]*(.[0-9]*)?$", message = "Incorrect format for amount")
    @Schema(description = "The amount to be paid", example = "0.00")
    private String amount;

    @NotBlank
    @JsonProperty("from_currency")
    @Pattern(regexp = "^[A-z]{3}$", message = "Incorrect format for currency")
    @Schema(description = "The currency used for issuing the payment.", example = "KES")
    private String fromCurrency;

    @Size(max = 100, message = "Exceeded size for reason")
    @Schema(description = "Optional input from user specifying reason.", example = "No reason")
    private String reason;

    @Size(max = 100, message = "Exceeded size for reference")
    @Schema(description = "Optional input from user writing reference.", example = "No reference")
    private String reference;

}
