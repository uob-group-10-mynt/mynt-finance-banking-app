
package com.mynt.banking.currency_cloud.manage.transactions.requests;

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
public class CurrencyCloudFindTransactionsRequest {
    
    //@NotNull
    @JsonProperty("on_behalf_of")
    @Size(max = 255)
    @Builder.Default
    private String onBehalfOf = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    //@NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Builder.Default
    private String amount = "";
    
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
    @JsonProperty("action")
    @Size(max = 255)
    @Builder.Default
    private String action = "";
    
    //@NotNull
    @JsonProperty("related_entity_type")
    @Size(max = 255)
    @Builder.Default
    private String relatedEntityType = "";
    
    //@NotNull
    @JsonProperty("related_entity_id")
    @Size(max = 255)
    @Builder.Default
    private String relatedEntityId = "";
    
    //@NotNull
    @JsonProperty("related_entity_short_reference")
    @Size(max = 255)
    @Builder.Default
    private String relatedEntityShortReference = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("type")
    @Size(max = 255)
    @Builder.Default
    private String type = "";
    
    //@NotNull
    @JsonProperty("reason")
    @Size(max = 255)
    @Builder.Default
    private String reason = "";
    
    //@NotNull
    @JsonProperty("settles_at_from")
    @Size(max = 255)
    @Builder.Default
    private String settlesAtFrom = "";
    
    //@NotNull
    @JsonProperty("settles_at_to")
    @Size(max = 255)
    @Builder.Default
    private String settlesAtTo = "";
    
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
    @JsonProperty("completed_at_from")
    @Size(max = 255)
    @Builder.Default
    private String completedAtFrom = "";
    
    //@NotNull
    @JsonProperty("completed_at_to")
    @Size(max = 255)
    @Builder.Default
    private String completedAtTo = "";
    
    //@NotNull
    @JsonProperty("beneficiary_id")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryId = "";
    
    //@NotNull
    @JsonProperty("currency_pair")
    @Size(max = 255)
    @Builder.Default
    private String currencyPair = "";
    
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