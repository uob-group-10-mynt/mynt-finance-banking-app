
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
public class CurrencyCloudFindAccountsRequest {
    
    //@NotNull
    @JsonProperty("account_name")
    @Size(max = 255)
    @Builder.Default
    private String accountName = "";
    
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
    @JsonProperty("street")
    @Size(max = 255)
    @Builder.Default
    private String street = "";
    
    //@NotNull
    @JsonProperty("city")
    @Size(max = 255)
    @Builder.Default
    private String city = "";
    
    //@NotNull
    @JsonProperty("state_or_province")
    @Size(max = 255)
    @Builder.Default
    private String stateOrProvince = "";
    
    //@NotNull
    @JsonProperty("postal_code")
    @Size(max = 255)
    @Builder.Default
    private String postalCode = "";
    
    //@NotNull
    @JsonProperty("country")
    @Size(max = 255)
    @Builder.Default
    private String country = "";
    
    //@NotNull
    @JsonProperty("spread_table")
    @Size(max = 255)
    @Builder.Default
    private String spreadTable = "";
    
    //@NotNull
    @JsonProperty("bank_account_verified")
    @Size(max = 255)
    @Builder.Default
    private String bankAccountVerified = "";
    
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
    
}