
package com.mynt.banking.currency_cloud.manage.accounts.requests;

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
public class CurrencyCloudCreateAccountRequest {
    
    @NotNull
    @JsonProperty("account_name")
    @Size(max = 255)
    @Builder.Default
    private String accountName = "";
    
    @NotNull
    @JsonProperty("legal_entity_type")
    @Size(max = 255)
    @Builder.Default
    private String legalEntityType = "";
    
    @NotNull
    @JsonProperty("street")
    @Size(max = 255)
    @Builder.Default
    private String street = "";
    
    @NotNull
    @JsonProperty("city")
    @Size(max = 255)
    @Builder.Default
    private String city = "";
    
    //@NotNull
    @JsonProperty("postal_code")
    @Size(max = 255)
    @Builder.Default
    private String postalCode = "";
    
    @NotNull
    @JsonProperty("country")
    @Size(max = 255)
    @Builder.Default
    private String country = "";
    
    //@NotNull
    @JsonProperty("brand")
    @Size(max = 255)
    @Builder.Default
    private String brand = "";
    
    //@NotNull
    @JsonProperty("your_reference")
    @Size(max = 255)
    @Builder.Default
    private String yourReference = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("state_or_province")
    @Size(max = 255)
    @Builder.Default
    private String stateOrProvince = "";
    
    //@NotNull
    @JsonProperty("spread_table")
    @Size(max = 255)
    @Builder.Default
    private String spreadTable = "";
    
    //@NotNull
    @JsonProperty("api_trading")
    @Size(max = 255)
    @Builder.Default
    private String apiTrading = "";
    
    //@NotNull
    @JsonProperty("online_trading")
    @Size(max = 255)
    @Builder.Default
    private String onlineTrading = "";
    
    //@NotNull
    @JsonProperty("phone_trading")
    @Size(max = 255)
    @Builder.Default
    private String phoneTrading = "";
    
    //@NotNull
    @JsonProperty("identification_type")
    @Size(max = 255)
    @Builder.Default
    private String identificationType = "";
    
    //@NotNull
    @JsonProperty("identification_value")
    @Size(max = 255)
    @Builder.Default
    private String identificationValue = "";
    
    //@NotNull
    @JsonProperty("terms_and_conditions_accepted")
    @Size(max = 255)
    @Builder.Default
    private String termsAndConditionsAccepted = "";
    
}