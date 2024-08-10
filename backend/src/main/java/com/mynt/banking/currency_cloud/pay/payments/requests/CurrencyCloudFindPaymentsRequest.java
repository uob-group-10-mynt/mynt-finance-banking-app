
package com.mynt.banking.currency_cloud.pay.payments.requests;

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
public class CurrencyCloudFindPaymentsRequest {
    
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
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("reason")
    @Size(max = 255)
    @Builder.Default
    private String reason = "";
    
    //@NotNull
    @JsonProperty("payment_date_from")
    @Size(max = 255)
    @Builder.Default
    private String paymentDateFrom = "";
    
    //@NotNull
    @JsonProperty("payment_date_to")
    @Size(max = 255)
    @Builder.Default
    private String paymentDateTo = "";
    
    //@NotNull
    @JsonProperty("transferred_at_from")
    @Size(max = 255)
    @Builder.Default
    private String transferredAtFrom = "";
    
    //@NotNull
    @JsonProperty("transferred_at_to")
    @Size(max = 255)
    @Builder.Default
    private String transferredAtTo = "";
    
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
    @JsonProperty("beneficiary_id")
    @Size(max = 255)
    @Builder.Default
    private String beneficiaryId = "";
    
    //@NotNull
    @JsonProperty("conversion_id")
    @Size(max = 255)
    @Builder.Default
    private String conversionId = "";
    
    //@NotNull
    @JsonProperty("with_deleted")
    @Size(max = 255)
    @Builder.Default
    private String withDeleted = "";
    
    //@NotNull
    @JsonProperty("payment_group_id")
    @Size(max = 255)
    @Builder.Default
    private String paymentGroupId = "";
    
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
    
    //@NotNull
    @JsonProperty("charge_type")
    @Size(max = 255)
    @Builder.Default
    private String chargeType = "";
    
    //@NotNull
    @JsonProperty("review_status")
    @Size(max = 255)
    @Builder.Default
    private String reviewStatus = "";
    
}