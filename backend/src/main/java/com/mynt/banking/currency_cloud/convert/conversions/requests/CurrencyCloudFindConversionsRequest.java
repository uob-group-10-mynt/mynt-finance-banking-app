
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
public class CurrencyCloudFindConversionsRequest {
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("short_reference")
    @Size(max = 255)
    @Builder.Default
    private String shortReference = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
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
    @JsonProperty("conversion_ids[]")
    @Size(max = 255)
    @Builder.Default
    private String conversionIds = "";
    
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
    @JsonProperty("currency_pair")
    @Size(max = 255)
    @Builder.Default
    private String currencyPair = "";
    
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
    @JsonProperty("buy_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String buyAmountFrom = "";
    
    //@NotNull
    @JsonProperty("buy_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String buyAmountTo = "";
    
    //@NotNull
    @JsonProperty("sell_amount_from")
    @Size(max = 255)
    @Builder.Default
    private String sellAmountFrom = "";
    
    //@NotNull
    @JsonProperty("sell_amount_to")
    @Size(max = 255)
    @Builder.Default
    private String sellAmountTo = "";
    
    //@NotNull
    @JsonProperty("scope")
    @Size(max = 255)
    @Builder.Default
    private String scope = "";
    
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
    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Builder.Default
    private String uniqueRequestId = "";
    
    //@NotNull
    @JsonProperty("bulk_upload_id")
    @Size(max = 255)
    @Builder.Default
    private String bulkUploadId = "";
    
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