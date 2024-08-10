
package com.mynt.banking.currency_cloud.pay.transfers.requests;

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
public class CurrencyCloudFindTransfersRequest {
    
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
    @JsonProperty("source_account_id")
    @Size(max = 255)
    @Builder.Default
    private String sourceAccountId = "";
    
    //@NotNull
    @JsonProperty("destination_account_id")
    @Size(max = 255)
    @Builder.Default
    private String destinationAccountId = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
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
    @JsonProperty("creator_contact_id")
    @Size(max = 255)
    @Builder.Default
    private String creatorContactId = "";
    
    //@NotNull
    @JsonProperty("creator_account_id")
    @Size(max = 255)
    @Builder.Default
    private String creatorAccountId = "";
    
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
    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Builder.Default
    private String uniqueRequestId = "";
    
}