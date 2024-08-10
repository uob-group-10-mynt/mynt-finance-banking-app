
package com.mynt.banking.currency_cloud.pay.beneficiaries.requests;

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
public class CurrencyCloudVerifyBeneficiaryAccountRequest {
    
    //@NotNull
    @JsonProperty("payment_type")
    @Size(max = 255)
    @Builder.Default
    private String paymentType = "";
    
    @NotNull
    @JsonProperty("bank_country")
    @Size(max = 255)
    @Builder.Default
    private String bankCountry = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    @NotNull
    @JsonProperty("account_number")
    @Size(max = 255)
    @Builder.Default
    private String accountNumber = "";
    
    @NotNull
    @JsonProperty("beneficiary_entity_type")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryEntityType = "";
    
    //@NotNull
    @JsonProperty("beneficiary_company_name")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryCompanyName = "";
    
    //@NotNull
    @JsonProperty("beneficiary_first_name")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryFirstName = "";
    
    //@NotNull
    @JsonProperty("beneficiary_last_name")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryLastName = "";
    
    //@NotNull
    @JsonProperty("routing_code_type_1")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeType1 = "";
    
    @NotNull
    @JsonProperty("routing_code_value_1")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeValue1 = "";
    
    //@NotNull
    @JsonProperty("routing_code_type_2")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeType2 = "";
    
    //@NotNull
    @JsonProperty("routing_code_value_2")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeValue2 = "";
    
    //@NotNull
    @JsonProperty("bic_swift")
    @Size(max = 255)
    @Builder.Default
    private String bicSwift = "";
    
    //@NotNull
    @JsonProperty("iban")
    @Size(max = 255)
    @Builder.Default
    private String iban = "";
    
    //@NotNull
    @JsonProperty("secondary_reference_data")
    @Size(max = 255)
    @Builder.Default
    private String secondaryReferenceData = "";
    
}