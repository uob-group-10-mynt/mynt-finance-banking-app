
package com.mynt.banking.currency_cloud.convert.conversions.requests;

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
public class CurrencyCloudRetrieveProfitLossRequest {
    
    //@NotNull
    @JsonProperty("account_id")
    @Size(max = 255)
    @Builder.Default
    private String accountId = "";
    
    //@NotNull
    @JsonProperty("contact_id")
    @Size(max = 255)
    @Builder.Default
    private String contactId = "";
    
    //@NotNull
    @JsonProperty("conversion_id")
    @Size(max = 255)
    @Builder.Default
    private String conversionId = "";
    
    //@NotNull
    @JsonProperty("event_type")
    @Size(max = 255)
    @Builder.Default
    private String eventType = "";
    
    //@NotNull
    @JsonProperty("event_date_time_from")
    @Size(max = 255)
    @Builder.Default
    private String eventDateTimeFrom = "";
    
    //@NotNull
    @JsonProperty("event_date_time_to")
    @Size(max = 255)
    @Builder.Default
    private String eventDateTimeTo = "";
    
    //@NotNull
    @JsonProperty("amount_from")
    @Size(max = 255)
    @Builder.Default
    private String amountFrom = "";
    
    //@NotNull
    @JsonProperty("amount_to")
    @Size(max = 255)
    @Builder.Default
    private String amountTo = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
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
    
}