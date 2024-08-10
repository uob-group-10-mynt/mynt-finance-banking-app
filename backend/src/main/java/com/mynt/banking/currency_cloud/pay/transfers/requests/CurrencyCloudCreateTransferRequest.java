
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
public class CurrencyCloudCreateTransferRequest {
    
    @NotNull
    @JsonProperty("source_account_id")
    @Size(max = 255)
    @Builder.Default
    private String sourceAccountId = "";
    
    @NotNull
    @JsonProperty("destination_account_id")
    @Size(max = 255)
    @Builder.Default
    private String destinationAccountId = "";
    
    @NotNull
    @JsonProperty("currency")
    @Size(max = 255)
    @Builder.Default
    private String currency = "";
    
    @NotNull
    @JsonProperty("amount")
    @Size(max = 255)
    @Builder.Default
    private String amount = "";
    
    //@NotNull
    @JsonProperty("reason")
    @Size(max = 255)
    @Builder.Default
    private String reason = "";
    
    //@NotNull
    @JsonProperty("unique_request_id")
    @Size(max = 255)
    @Builder.Default
    private String uniqueRequestId = "";
    
}