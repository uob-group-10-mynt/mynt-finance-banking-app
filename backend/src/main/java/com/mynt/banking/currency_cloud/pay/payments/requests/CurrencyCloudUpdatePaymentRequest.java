
package com.mynt.banking.currency_cloud.pay.payments.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CurrencyCloudUpdatePaymentRequest {
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    //@NotNull
    @JsonProperty("beneficiary_id")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryId = "";
    
    //@NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Builder.Default
    private String amount = "";
    
    //@NotNull
    @JsonProperty("payment_date")
    @Size(max = 255)
    @Builder.Default
    private String paymentDate = "";
    
    //@NotNull
    @JsonProperty("reason")
    @Size(max = 255)
    @Builder.Default
    private String reason = "";
    
    //@NotNull
    @JsonProperty("payment_type")
    @Size(max = 255)
    @Builder.Default
    private String paymentType = "";
    
    //@NotNull
    @JsonProperty("reference")
    @Size(max = 255)
    @Builder.Default
    private String reference = "";
    
    //@NotNull
    @JsonProperty("conversion_id")
    @Size(max = 255)
    @Builder.Default
    private String conversionId = "";
    
    //@NotNull
    @JsonProperty("payer_entity_type")
    @Size(max = 255)
    @Builder.Default
    private String payerEntityType = "";
    
    //@NotNull
    @JsonProperty("payer_company_name")
    @Size(max = 255)
    @Builder.Default
    private String payerCompanyName = "";
    
    //@NotNull
    @JsonProperty("payer_first_name")
    @Size(max = 255)
    @Builder.Default
    private String payerFirstName = "";
    
    //@NotNull
    @JsonProperty("payer_last_name")
    @Size(max = 255)
    @Builder.Default
    private String payerLastName = "";
    
    //@NotNull
    @JsonProperty("payer_city")
    @Size(max = 255)
    @Builder.Default
    private String payerCity = "";
    
    //@NotNull
    @JsonProperty("payer_address")
    @Size(max = 255)
    @Builder.Default
    private String payerAddress = "";
    
    //@NotNull
    @JsonProperty("payer_postcode")
    @Size(max = 255)
    @Builder.Default
    private String payerPostcode = "";
    
    //@NotNull
    @JsonProperty("payer_state_or_province")
    @Size(max = 255)
    @Builder.Default
    private String payerStateOrProvince = "";
    
    //@NotNull
    @JsonProperty("payer_country")
    @Size(max = 255)
    @Builder.Default
    private String payerCountry = "";
    
    //@NotNull
    @JsonProperty("payer_date_of_birth")
    @Size(max = 255)
    @Builder.Default
    private String payerDateOfBirth = "";
    
    //@NotNull
    @JsonProperty("payer_identification_type")
    @Size(max = 255)
    @Builder.Default
    private String payerIdentificationType = "";
    
    //@NotNull
    @JsonProperty("payer_identification_value")
    @Size(max = 255)
    @Builder.Default
    private String payerIdentificationValue = "";
    
    //@NotNull
    @JsonProperty("ultimate_beneficiary_name")
    @Size(max = 255)
    @Builder.Default
    private String ultimateBeneficiaryName = "";
    
    //@NotNull
    @JsonProperty("purpose_code")
    @Size(max = 255)
    @Builder.Default
    private String purposeCode = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("charge_type")
    @Size(max = 255)
    @Builder.Default
    private String chargeType = "";
    
    //@NotNull
    @JsonProperty("fee_amount")
    @Size(max = 255)
    @Builder.Default
    private String feeAmount = "";
    
    //@NotNull
    @JsonProperty("fee_currency")
    @Size(max = 255)
    @Builder.Default
    private String feeCurrency = "";
    
    //@NotNull
    @JsonProperty("invoice_number")
    @Size(max = 255)
    @Builder.Default
    private String invoiceNumber = "";
    
    //@NotNull
    @JsonProperty("invoice_date")
    @Size(max = 255)
    @Builder.Default
    private String invoiceDate = "";
    
}