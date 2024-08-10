
package com.mynt.banking.currency_cloud.collect.funding.requests;

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
public class CurrencyCloudFindFundingAccountsRequest {
    
    //@NotNull
    @JsonProperty("payment_type")
    @Size(max = 255)
    @Builder.Default
    private String paymentType = "";
    
    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    //@NotNull
    @JsonProperty("account_id")
    @Size(max = 255)
    @Builder.Default
    private String accountId = "";
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
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