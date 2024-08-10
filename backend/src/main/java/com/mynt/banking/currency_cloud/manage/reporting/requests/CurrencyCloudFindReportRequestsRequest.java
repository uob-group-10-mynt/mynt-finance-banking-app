
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
public class CurrencyCloudFindReportRequestsRequest {
    
    //@NotNull
    @JsonProperty("short_reference")
    @Size(max = 255)
    @Builder.Default
    private String shortReference = "";
    
    //@NotNull
    @JsonProperty("description")
    @Size(max = 255)
    @Builder.Default
    private String description = "";
    
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
    @JsonProperty("expiration_date_from")
    @Size(max = 255)
    @Builder.Default
    private String expirationDateFrom = "";
    
    //@NotNull
    @JsonProperty("expiration_date_to")
    @Size(max = 255)
    @Builder.Default
    private String expirationDateTo = "";
    
    //@NotNull
    @JsonProperty("status")
    @Size(max = 255)
    @Builder.Default
    private String status = "";
    
    //@NotNull
    @JsonProperty("report_type")
    @Size(max = 255)
    @Builder.Default
    private String reportType = "";
    
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
    @JsonProperty("scope")
    @Size(max = 255)
    @Builder.Default
    private String scope = "";
    
}