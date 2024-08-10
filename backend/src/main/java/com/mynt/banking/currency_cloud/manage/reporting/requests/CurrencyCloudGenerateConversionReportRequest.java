
package com.mynt.banking.currency_cloud.manage.reporting.requests;

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
public class CurrencyCloudGenerateConversionReportRequest {
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("description")
    @Size(max = 255)
    @Builder.Default
    private String description = "";
    
    //@NotNull
    @JsonProperty("buy_currency")
    @Size(max = 255)
    @Builder.Default
    private String buyCurrency = "";
    
    //@NotNull
    @JsonProperty("sell_currency")
    @Size(max = 255)
    @Builder.Default
    private String sellCurrency = "";
    
    //@NotNull
    @JsonProperty("client_buy_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String clientBuyAmountFrom = "";
    
    //@NotNull
    @JsonProperty("client_buy_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String clientBuyAmountTo = "";
    
    //@NotNull
    @JsonProperty("client_sell_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String clientSellAmountFrom = "";
    
    //@NotNull
    @JsonProperty("client_sell_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String clientSellAmountTo = "";
    
    //@NotNull
    @JsonProperty("partner_buy_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String partnerBuyAmountFrom = "";
    
    //@NotNull
    @JsonProperty("partner_buy_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String partnerBuyAmountTo = "";
    
    //@NotNull
    @JsonProperty("partner_sell_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String partnerSellAmountFrom = "";
    
    //@NotNull
    @JsonProperty("partner_sell_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String partnerSellAmountTo = "";
    
    //@NotNull
    @JsonProperty("client_status")
    @Size(max = 255)
    @Builder.Default
    private String clientStatus = "";
    
    //@NotNull
    @JsonProperty("conversion_date_from")
    @Size(max = 255)
    @Builder.Default
    private String conversionDateFrom = "";
    
    //@NotNull
    @JsonProperty("conversion_date_to")
    @Size(max = 255)
    @Builder.Default
    private String conversionDateTo = "";
    
    //@NotNull
    @JsonProperty("settlement_date_from")
    @Size(max = 255)
    @Builder.Default
    private String settlementDateFrom = "";
    
    //@NotNull
    @JsonProperty("settlement_date_to")
    @Size(max = 255)
    @Builder.Default
    private String settlementDateTo = "";
    
    //@NotNull
    @JsonProperty("created_at_from")
    @Size(max = 255)
    @Builder.Default
    private String createdAtFrom = "";
    
    //@NotNull
    @JsonProperty("created_at_to")
    @Size(max = 255)
    @Builder.Default
    private String createdAtTo = "";
    
    //@NotNull
    @JsonProperty("updated_at_from")
    @Size(max = 255)
    @Builder.Default
    private String updatedAtFrom = "";
    
    //@NotNull
    @JsonProperty("updated_at_to")
    @Size(max = 255)
    @Builder.Default
    private String updatedAtTo = "";
    
    //@NotNull
    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Builder.Default
    private String uniqueRequestId = "";
    
    //@NotNull
    @JsonProperty("scope")
    @Size(max = 255)
    @Builder.Default
    private String scope = "";
    
}