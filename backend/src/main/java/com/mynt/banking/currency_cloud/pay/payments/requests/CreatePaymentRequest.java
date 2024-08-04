package com.mynt.banking.currency_cloud.pay.payments.requests;

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
@Schema(description = "CreatePaymentRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreatePaymentRequest {

    @JsonProperty("currency")
    @NotNull
    @Size(min = 3, max = 3)
    @Schema(description = "Currency of the payment", example = "KES")
    private String currency;

    @JsonProperty("beneficiary_id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "ID of the beneficiary", example = "547ace33-dee8-41eb-b1c7-9aed2c23e23a")
    private String beneficiaryId;

    @JsonProperty("amount")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Amount to be paid", example = "1000.00")
    private String amount;

    @JsonProperty("reason")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Reason for the payment", example = "Invoice payment")
    private String reason;

    @JsonProperty("reference")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Reference for the payment", example = "INV-12345")
    private String reference;

    @JsonProperty("unique_request_id")
    @NotNull
    @Size(max = 255)
    @Schema(description = "Unique ID for the request", example = "req-12345")
    private String uniqueRequestId;

    @JsonProperty("payment_date")
    @Size(max = 255)
    @Schema(description = "Date of the payment", example = " ")
    @Builder.Default
    private String paymentDate = "";

    @JsonProperty("payment_type")
    @Size(max = 255)
    @Schema(description = "Type of the payment", example = " ")
    @Builder.Default
    private String paymentType  = "";

    @JsonProperty("conversion_id")
    @Size(max = 255)
    @Schema(description = "ID of the conversion", example = " ")
    @Builder.Default
    private String conversionId = "";

    @JsonProperty("payer_entity_type")
    @Size(max = 255)
    @Schema(description = "Entity type of the payer", example = " ")
    @Builder.Default
    private String payerEntityType = "";

    @JsonProperty("payer_company_name")
    @Size(max = 255)
    @Schema(description = "Company name of the payer", example = " ")
    @Builder.Default
    private String payerCompanyName = "";

    @JsonProperty("payer_first_name")
    @Size(max = 255)
    @Schema(description = "First name of the payer", example = " ")
    @Builder.Default
    private String payerFirstName = "";

    @JsonProperty("payer_last_name")
    @Size(max = 255)
    @Schema(description = "Last name of the payer", example = " ")
    @Builder.Default
    private String payerLastName = "";

    @JsonProperty("payer_city")
    @Size(max = 255)
    @Schema(description = "City of the payer", example = " ")
    @Builder.Default
    private String payerCity = "";

    @JsonProperty("payer_address")
    @Size(max = 255)
    @Schema(description = "Address of the payer", example = " ")
    @Builder.Default
    private String payerAddress = "";

    @JsonProperty("payer_postcode")
    @Size(max = 255)
    @Schema(description = "Postcode of the payer", example = " ")
    @Builder.Default
    private String payerPostcode = "";

    @JsonProperty("payer_state_or_province")
    @Size(max = 255)
    @Schema(description = "State or province of the payer", example = " ")
    @Builder.Default
    private String payerStateOrProvince = "";

    @JsonProperty("payer_country")
    @Size(max = 2)
    @Schema(description = "Country of the payer", example = " ")
    @Builder.Default
    private String payerCountry = "";

    @JsonProperty("payer_date_of_birth")
    @Size(max = 255)
    @Schema(description = "Date of birth of the payer", example = " ")
    @Builder.Default
    private String payerDateOfBirth = "";

    @JsonProperty("payer_identification_type")
    @Size(max = 255)
    @Schema(description = "Identification type of the payer", example = " ")
    @Builder.Default
    private String payerIdentificationType = "";

    @JsonProperty("payer_identification_value")
    @Size(max = 255)
    @Schema(description = "Identification value of the payer", example = " ")
    @Builder.Default
    private String payerIdentificationValue = "";

    @JsonProperty("ultimate_beneficiary_name")
    @Size(max = 255)
    @Schema(description = "Ultimate beneficiary name", example = " ")
    @Builder.Default
    private String ultimateBeneficiaryName = "";

    @JsonProperty("purpose_code")
    @Size(max = 255)
    @Schema(description = "Purpose code for the payment", example = " ")
    @Builder.Default
    private String purposeCode = "";

    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Schema(description = "On behalf of information", example = " ")
    @Builder.Default
    private String onBehalfOf = "";

    @JsonProperty("charge_type")
    @Size(max = 255)
    @Schema(description = "Type of charge", example = " ")
    @Builder.Default
    private String chargeType = "";

    @JsonProperty("fee_amount")
    @Size(max = 255)
    @Schema(description = "Fee amount for the payment", example = " ")
    @Builder.Default
    private String feeAmount = "";

    @JsonProperty("fee_currency")
    @Size(max = 3)
    @Schema(description = "Currency of the fee", example = " ")
    @Builder.Default
    private String feeCurrency = "";

    @JsonProperty("invoice_number")
    @Size(max = 255)
    @Schema(description = "Invoice number", example = " ")
    @Builder.Default
    private String invoiceNumber = "";

    @JsonProperty("invoice_date")
    @Size(max = 255)
    @Schema(description = "Invoice date", example = " ")
    @Builder.Default
    private String invoiceDate = "";
}

