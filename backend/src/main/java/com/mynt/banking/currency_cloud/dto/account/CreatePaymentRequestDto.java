package com.mynt.banking.currency_cloud.dto.account;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "")
public class CreatePaymentRequestDto {

    @JsonProperty("amount")
    @NotNull
    @Schema(description = "Amount to be paid", example = "1000.00")
    private BigDecimal amount;

    @JsonProperty("currency")
    @NotNull
    @Size(max = 3)
    @Schema(description = "Currency of the payment", example = "USD")
    private String currency;

    @JsonProperty("beneficiary_id")
    @NotNull
    @Schema(description = "ID of the beneficiary", example = "e74e80b5-0b6e-45a4-b3d5-1d3d40821b3d")
    private String beneficiaryId;

    @JsonProperty("reason")
    @Size(max = 255)
    @Schema(description = "Reason for the payment", example = "Invoice payment")
    private String reason;

    @JsonProperty("reference")
    @Size(max = 255)
    @Schema(description = "Reference for the payment", example = "INV-12345")
    private String reference;

    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Schema(description = "Unique identifier for the request", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uniqueRequestId;

    @JsonProperty("payment_type")
    @Size(max = 255)
    @Schema(description = "Type of payment", example = "regular")
    private String paymentType;

    @JsonProperty("conversion_id")
    @Size(max = 36)
    @Schema(description = "Conversion ID if the payment is a conversion payment", example = "81b51a72-0a30-404e-a4c6-9f426c39659b")
    private String conversionId;

    @JsonProperty("on_behalf_of")
    @Size(max = 36)
    @Schema(description = "ID of the contact on behalf of whom the request is being made", example = "7c223df3-14fa-42e2-898f-e27a184db4c2")
    private String onBehalfOf;

    @JsonProperty("charge_type")
    @Size(max = 255)
    @Schema(description = "Charge type for the payment", example = "shared")
    private String chargeType;

    @JsonProperty("ultimate_beneficiary_name")
    @Size(max = 255)
    @Schema(description = "Name of the ultimate beneficiary", example = "John Doe")
    private String ultimateBeneficiaryName;
}