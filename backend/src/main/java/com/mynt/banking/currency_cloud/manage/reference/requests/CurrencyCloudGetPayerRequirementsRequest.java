
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
public class CurrencyCloudGetPayerRequirementsRequest {
    
    @NotNull
    @JsonProperty("payer_country")
    @Size(max = 255)
    @Builder.Default
    private String payerCountry = "";
    
    //@NotNull
    @JsonProperty("payer_entity_type")
    @Size(max = 255)
    @Builder.Default
    private String payerEntityType = "";
    
    //@NotNull
    @JsonProperty("payment_type")
    @Size(max = 255)
    @Builder.Default
    private String paymentType = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
}