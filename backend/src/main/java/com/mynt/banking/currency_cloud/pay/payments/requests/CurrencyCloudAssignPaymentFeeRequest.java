
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
public class CurrencyCloudAssignPaymentFeeRequest {
    
    @NotNull
    @JsonProperty("payment_fee_id")
    @Size(max = 255)
    @Builder.Default
    private String paymentFeeId = "";
    
    @NotNull
    @JsonProperty("account_id")
    @Size(max = 255)
    @Builder.Default
    private String accountId = "";
    
}