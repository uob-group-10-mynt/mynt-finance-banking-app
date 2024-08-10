
package com.mynt.banking.currency_cloud.manage.reference.requests;

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
public class CurrencyCloudGetBeneficiaryRequirementsRequest {
    
    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    @NotNull
    @JsonProperty("bank_account_country")
    @Size(max = 255)
    @Builder.Default
    private String bankAccountCountry = "";
    
    @NotNull
    @JsonProperty("beneficiary_country")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryCountry = "";
    
}