
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
public class CurrencyCloudFindBeneficiariesRequest {
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("bank_account_holder_name")
    @Size(max = 255)
    @Builder.Default
    private String bankAccountHolderName = "";
    
    //@NotNull
    @JsonProperty("beneficiary_country")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryCountry = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    //@NotNull
    @JsonProperty("account_number")
    @Size(max = 255)
    @Builder.Default
    private String accountNumber = "";
    
    //@NotNull
    @JsonProperty("routing_code_type[0]")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeType = "";
    
    //@NotNull
    @JsonProperty("routing_code_value[0]")
    @Size(max = 255)
    @Builder.Default
    private String routingCodeValue = "";
    
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
    @JsonProperty("default_beneficiary")
    @Size(max = 255)
    @Builder.Default
    private String defaultBeneficiary = "";
    
    //@NotNull
    @JsonProperty("bank_name")
    @Size(max = 255)
    @Builder.Default
    private String bankName = "";
    
    //@NotNull
    @JsonProperty("bank_account_type")
    @Size(max = 255)
    @Builder.Default
    private String bankAccountType = "";
    
    //@NotNull
    @JsonProperty("name")
    @Size(max = 255)
    @Builder.Default
    private String name = "";
    
    //@NotNull
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
    @JsonProperty("beneficiary_city")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryCity = "";
    
    //@NotNull
    @JsonProperty("beneficiary_postcode")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryPostcode = "";
    
    //@NotNull
    @JsonProperty("beneficiary_state_or_province")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryStateOrProvince = "";
    
    //@NotNull
    @JsonProperty("beneficiary_date_of_birth")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryDateOfBirth = "";
    
    //@NotNull
    @JsonProperty("scope")
    @Size(max = 255)
    @Builder.Default
    private String scope = "";
    
    //@NotNull
    @JsonProperty("page")
    @Size(max = 255)
    @Builder.Default
    private String page = "";
    
    //@NotNull
    @JsonProperty("per_page")
    @Size(max = 255)
    @Builder.Default
    private String perPage = "";
    
    //@NotNull
    @JsonProperty("order")
    @Size(max = 255)
    @Builder.Default
    private String order = "";
    
    //@NotNull
    @JsonProperty("order_asc_desc")
    @Size(max = 255)
    @Builder.Default
    private String orderAscDesc = "";
    
    //@NotNull
    @JsonProperty("beneficiary_external_reference")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryExternalReference = "";
    
}